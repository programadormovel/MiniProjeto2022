package br.edu.fieb.miniprojeto2022.registro.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class RegistroResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    RegistroResult(@Nullable Integer error) {
        this.error = error;
    }

    RegistroResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}