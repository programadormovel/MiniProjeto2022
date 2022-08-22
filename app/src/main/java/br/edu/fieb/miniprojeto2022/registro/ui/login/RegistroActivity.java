package br.edu.fieb.miniprojeto2022.registro.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import br.edu.fieb.miniprojeto2022.MenuActivity;
import br.edu.fieb.miniprojeto2022.R;
import br.edu.fieb.miniprojeto2022.registro.ui.login.LoggedInUserView;
import br.edu.fieb.miniprojeto2022.registro.ui.login.RegistroViewModel;
import br.edu.fieb.miniprojeto2022.registro.ui.login.RegistroViewModelFactory;
import br.edu.fieb.miniprojeto2022.databinding.ActivityRegistroBinding;
import br.edu.fieb.miniprojeto2022.ui.login.LoginActivity;

public class RegistroActivity extends AppCompatActivity {

    private RegistroViewModel registroViewModel;
    private ActivityRegistroBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registroViewModel = new ViewModelProvider(this, new RegistroViewModelFactory())
                .get(RegistroViewModel.class);

        final EditText usernameEditText = binding.usernameRegistro;
        final EditText passwordEditText = binding.passwordRegistro;
        final Button registroButton = binding.loginRegistro;
        final ProgressBar loadingProgressBar = binding.loadingRegistro;

        registroViewModel.getRegistroFormState().observe(this, new Observer<RegistroFormState>() {
            @Override
            public void onChanged(@Nullable RegistroFormState registroFormState) {
                if (registroFormState == null) {
                    return;
                }
                registroButton.setEnabled(registroFormState.isDataValid());
                if (registroFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registroFormState.getUsernameError()));
                }
                if (registroFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registroFormState.getPasswordError()));
                }
            }
        });

        registroViewModel.getRegistroResult().observe(this, new Observer<RegistroResult>() {
            @Override
            public void onChanged(@Nullable RegistroResult registroResult) {
                if (registroResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registroResult.getError() != null) {
                    showRegistroFailed(registroResult.getError());
                }
                if (registroResult.getSuccess() != null) {
                    updateUiWithUser(registroResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy registro activity once successful
                // finish();

                Intent it = new Intent(RegistroActivity.this,
                        LoginActivity.class);
                startActivity(it);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registroViewModel.registroDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registroViewModel.registro(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registroViewModel.registro(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showRegistroFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}