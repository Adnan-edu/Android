package com.example.movieapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.model.Credentials;
import com.example.movieapp.model.Person;
import com.example.movieapp.model.SignUpModel;
import com.example.movieapp.restapi.RestApi;
import com.example.movieapp.utility.Hashing;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private Spinner stateSpinner;
    private EditText firstNameEdt;
    private EditText surNameEdt;
    private EditText streetNameEdt;
    private EditText streetNumberEdt;
    private EditText postCodeEdt;
    private EditText userEmailEdt;
    private EditText userPasswordEdt;
    private ArrayAdapter<String> stateAdapter;
    private ArrayList<String> stateList;
    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    private Button signUpButton;
    private TextView dobAddTxtVw;
    private Button clickDobBtn;
    private RestApi restApi;
    private Button loginButton;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        stateSpinner = findViewById(R.id.stateNameId);
        firstNameEdt = findViewById(R.id.firstName);
        surNameEdt = findViewById(R.id.surName);
        streetNameEdt = findViewById(R.id.streetName);
        streetNumberEdt = findViewById(R.id.streetNumber);
        postCodeEdt = findViewById(R.id.postCode);
        userEmailEdt = findViewById(R.id.userEmail);
        userPasswordEdt = findViewById(R.id.userPassword);
        radioGenderGroup = findViewById(R.id.radioGender);
        signUpButton = findViewById(R.id.signupBtnId);
        dobAddTxtVw = findViewById(R.id.dateDobViewId);
        clickDobBtn = findViewById(R.id.dobPickerBtn);
        loginButton = findViewById(R.id.signInBtnIdFrmSngUp);
        restApi = new RestApi();


        assignState();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                SignUpModel signUpModel = fetchSignUpModelData();
                if (signUpModel != null) {
                    AddPersonTask addPersonTask = new AddPersonTask();
                    addPersonTask.execute(signUpModel);
                }
            }
        });

        clickDobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private SignUpModel fetchSignUpModelData() {
        LocalDate localdate = LocalDate.now();
        String year = Integer.toString(localdate.getYear());
        String month = Integer.toString(localdate.getMonthValue());
        String day = Integer.toString(localdate.getDayOfMonth());
        String signUpDate = localdate + "T00:00:00+11:00";


        SignUpModel signUpModel = new SignUpModel();
        Person person = new Person();
        Credentials credentials = new Credentials();
        String firstname = firstNameEdt.getText().toString().trim();
        String surname = surNameEdt.getText().toString().trim();

        int selectedId = radioGenderGroup.getCheckedRadioButtonId();
        radioGenderButton = (RadioButton) findViewById(selectedId);
        String gender = radioGenderButton.getText().toString();

        String userDob = dobAddTxtVw.getText().toString() + "T00:00:00+11:00";
        String streetnumber = streetNumberEdt.getText().toString();
        String streetname = streetNameEdt.getText().toString();
        String state = stateSpinner.getSelectedItem().toString();
        String postcode = postCodeEdt.getText().toString();
        String userEmail = userEmailEdt.getText().toString().trim();


        if (firstname.length() == 0 || surname.length() == 0 || streetname.length() == 0 || streetnumber.length() == 0 || postcode.length() == 0 || userEmail.length() == 0) {
            Toast.makeText(SignUpActivity.this, "firstname, surname, streetname, streetnumber, postcode, userEmail or password can not be empty", Toast.LENGTH_LONG).show();
            return null;
        } else if (!userEmail.matches(emailPattern)) {
            Toast.makeText(SignUpActivity.this, "Please provide valid email address format", Toast.LENGTH_LONG).show();
            return null;
        }

        person.setFirstname(firstname);
        person.setSurname(surname);
        person.setGender(gender);
        person.setDob(userDob);
        person.setState(state);
        person.setPostcode(postcode);
        person.setStreetname(streetname);
        person.setStreetnumber(streetnumber);
        person.setPostcode(postcode);


        String hashedVal = "";
        try {
            hashedVal = Hashing.toHexString(Hashing.getSHA(userPasswordEdt.getText().toString().trim()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        credentials.setPersonid(person);
        credentials.setUsername(userEmail);
        credentials.setPassword(hashedVal);
        credentials.setSignupdate(signUpDate);

/*        LocalDate localdate = LocalDate.now();
        String year = Integer.toString(localdate.getYear());
        String month = Integer.toString(localdate.getMonthValue());
        String day = Integer.toString(localdate.getDayOfMonth());
        String signUpDate = year+"-"+month+"-"+day+"T00:00:00+11:00";*/
        //credentials.setSignupdate(signUpDate);

        signUpModel.setPerson(person);
        signUpModel.setCredentials(credentials);
        return signUpModel;
    }

    private void assignState() {
        stateList = new ArrayList<>();
        stateList.add("Western Australia");
        stateList.add("Queensland");
        stateList.add("New South Wales");
        stateList.add("Victoria");
        stateList.add("South Australia");
        stateList.add("Tasmania");
        stateList.add("Northern Territory");
        stateList.add("Australian Capital Territory");
        stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateList);
        stateSpinner.setAdapter(stateAdapter);
    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("yyyy-MM-dd", calendar1).toString();

                dobAddTxtVw.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private class AddPersonTask extends AsyncTask<SignUpModel, Void, SignUpModel> {
        @Override
        protected SignUpModel doInBackground(SignUpModel... signUpModel) {
            return signUpModel[0];
        }

        @Override
        protected void onPostExecute(SignUpModel result) {
            AddCredTask adCredTask = new AddCredTask();
            adCredTask.execute(result);

        }
    }

    private class AddCredTask extends AsyncTask<SignUpModel, Void, String> {
        @Override
        protected String doInBackground(SignUpModel... signUpModel) {
            return restApi.addCredentials(signUpModel[0]);
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
