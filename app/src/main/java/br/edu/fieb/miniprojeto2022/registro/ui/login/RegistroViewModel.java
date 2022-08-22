package br.edu.fieb.miniprojeto2022.registro.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import br.edu.fieb.miniprojeto2022.registro.data.RegistroRepository;
import br.edu.fieb.miniprojeto2022.registro.data.Result;
import br.edu.fieb.miniprojeto2022.registro.data.model.LoggedInUser;
import br.edu.fieb.miniprojeto2022.R;

public class RegistroViewModel extends ViewModel {

    private MutableLiveData<RegistroFormState> registroFormState = new MutableLiveData<>();
    private MutableLiveData<RegistroResult> registroResult = new MutableLiveData<>();
    private RegistroRepository registroRepository;

    RegistroViewModel(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    LiveData<RegistroFormState> getRegistroFormState() {
        return registroFormState;
    }

    LiveData<RegistroResult> getRegistroResult() {
        return registroResult;
    }

    public void registro(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = registroRepository.registro(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            registroResult.setValue(new RegistroResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            registroResult.setValue(new RegistroResult(R.string.login_failed));
        }
    }

    public void registroDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            registroFormState.setValue(new RegistroFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            registroFormState.setValue(new RegistroFormState(null, R.string.invalid_password));
        } else {
            registroFormState.setValue(new RegistroFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}