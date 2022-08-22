package br.edu.fieb.miniprojeto2022.registro.data;

import br.edu.fieb.miniprojeto2022.registro.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class RegistroRepository {

    private static volatile RegistroRepository instance;

    private RegistroDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private RegistroRepository(RegistroDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegistroRepository getInstance(RegistroDataSource dataSource) {
        if (instance == null) {
            instance = new RegistroRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> registro(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.registro(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}