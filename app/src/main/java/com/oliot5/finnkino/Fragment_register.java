package com.oliot5.finnkino;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Fragment_register extends Fragment {

    View view;

    EditText usernameEditText;
    EditText passwordEditText1;
    EditText passwordEditText2;
    TextView passwordCheckTextView;
    Button registerButton;
    String username = "";
    String password1 = "";
    String password2 = "";
    String fileName = "users.xml";
    int help;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            username = usernameEditText.getText().toString();
            password1 = passwordEditText1.getText().toString();
            password2 = passwordEditText2.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_registerwindow, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        usernameEditText = (EditText) this.view.findViewById(R.id.editTextUsernameRegister);
        usernameEditText.addTextChangedListener(textWatcher);

        passwordEditText1 = (EditText) this.view.findViewById(R.id.editTextPasswordRegister);
        passwordEditText1.addTextChangedListener(textWatcher);

        passwordEditText2 = (EditText) this.view.findViewById(R.id.editTextPasswordAgainRegister);
        passwordEditText2.addTextChangedListener(textWatcher);

        passwordCheckTextView = (TextView) this.view.findViewById(R.id.passwordCheckWindowRegister);

        registerButton = (Button) this.view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password1.equals(password2)) {
                    Document document = null;
                    help = 1;

                    String string = "";
                    InputStream inputStream = null;
                    try {
                        //inputStream = getContext().getAssets().open(fileName);
                        inputStream = getContext().openFileInput(fileName);

                        int size = inputStream.available();
                        byte[] buffer = new byte[size];
                        inputStream.read(buffer);
                        string = new String(buffer);
                        inputStream.close();
                        System.out.println("Tiedoston avaaminen onnistui!");
                        System.out.println(string);
                    } catch (IOException e) {
                        e.printStackTrace();
                        string = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                "<UsersInformation>\n" +
                                "</UserInformation>";
                    }

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;

                    //Convert String to document
                    try {
                        builder = factory.newDocumentBuilder();
                        document = builder.parse(new InputSource(new StringReader(string)));
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                    //root element
                    Element root = document.getDocumentElement();

                    //user element
                    Element user = document.createElement("User");
//                  name.appendChild(document.createTextNode(movieName));
                    root.appendChild(user);

                    //child: movie name
                    Element filmName = document.createElement("Username");
                    filmName.appendChild(document.createTextNode(username));
                    user.appendChild(filmName);

                    //child: stars
                    Element amountOfStars = document.createElement("Password");
                    amountOfStars.appendChild(document.createTextNode(password1));
                    user.appendChild(amountOfStars);

                    //Convert document to string
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer transformer;

                    String output = null;
                    try {
                        transformer = tf.newTransformer();
                        StringWriter writer = new StringWriter();
                        transformer.transform(new DOMSource(document), new StreamResult(writer));
                        output = writer.getBuffer().toString();
                    } catch (TransformerConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }

                    System.out.println(output);

                    try {
                        FileOutputStream writer = getContext().openFileOutput(fileName,0);
                        writer.write(output.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(help == 1){
                        help = 2;
                        sendToActivity();
                    }



                } else {
                    passwordCheckTextView.setText("Salasanat eivät täsmää.");
                }
            }
        });

    }
    public void sendToActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.putExtra("key", username);
        startActivity(intent);
    }
}
