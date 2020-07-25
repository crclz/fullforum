package fullforum.services;

public interface IAuth {
    boolean isLoggedIn();

    long userId();
}
