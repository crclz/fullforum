package crclz.fullforum.dependency;

import crclz.fullforum.services.IAuth;

public class FakeAuth implements IAuth {

    public long realUserId = -1;

    @Override
    public boolean isLoggedIn() {
        return realUserId != -1;
    }

    @Override
    public long userId() {
        if (!isLoggedIn()) {
            throw new IllegalStateException("User is not logged in ");
        } else {
            return realUserId;
        }
    }
}
