package crclz.fullforum.dependency;

import crclz.fullforum.services.IAuth;

public class FakeAuth implements IAuth {

    private long realUserId = -1;

    public long getRealUserId() {
        return realUserId;
    }

    public void setRealUserId(long realUserId) {
        this.realUserId = realUserId;
    }


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
