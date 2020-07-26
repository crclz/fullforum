package crclz.fullforum.dto.out;

public class IdDto {
    public String id;

    public IdDto(Long id) {
        this.id = id.toString();
    }

    public long getLongId() {
        return Long.parseLong(id);
    }
}
