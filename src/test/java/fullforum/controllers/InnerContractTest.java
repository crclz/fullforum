package fullforum.controllers;

import fullforum.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class InnerContractTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void badRequest_400() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=BAD_REQUEST"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("a"));
    }

    @Test
    void unauthorized_401() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=UNAUTHORIZED"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void forbidden_403() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=FORBIDDEN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void forbidden_404() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=NOT_FOUND"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void input_model_convert_string_to_long_test() throws Exception {
        mockMvc.perform(post("/internal/long-test").contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\":\"9223372036854775807\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("9223372036854775807"));
    }

    @Test
    void out_dto_not_having_long() throws Exception {
        String packageName = "fullforum.dto.out";
        List<Class<?>> classList = new ArrayList<>();
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

        // Filter .class files.
        File[] files = new File(root.getFile()).listFiles((dir, name) -> name.endsWith(".class"));

        // Find classes implementing ICommand.
        for (File file : files) {
            String className = file.getName().replaceAll(".class$", "");
            Class<?> cls = Class.forName(packageName + "." + className);
            classList.add(cls);
        }

        for (var c : classList) {
            var cnt = Arrays.stream(c.getFields())
                    .filter(f -> f.getType().equals(Long.class) || f.getType().equals(Long.TYPE))
                    .count();
            if (cnt != 0) {
                var message = String.format(
                        "%s contains Long or long. long will suffer precision loss in javascript.",
                        c.getName());
                throw new Exception(message);
            }
        }
    }
}
