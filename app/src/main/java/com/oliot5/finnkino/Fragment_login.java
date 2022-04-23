package com.oliot5.finnkino;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Fragment_login extends Fragment {

    View view;

    EditText usernameEditText;
    EditText passwordEditText;
    TextView combinationCheckTextView;
    Button buttonLogin;
    String username = "";
    String password = "";
    String filename = "users.xml";
    int help;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            username = usernameEditText.getText().toString();
            password = passwordEditText.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_loginwindow, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        usernameEditText = (EditText) this.view.findViewById(R.id.editTextUsernameLogin);
        usernameEditText.addTextChangedListener(textWatcher);

        passwordEditText = (EditText) this.view.findViewById(R.id.editTextPasswordLogin);
        passwordEditText.addTextChangedListener(textWatcher);

        combinationCheckTextView = (TextView) this.view.findViewById(R.id.combinationCheckWindowLogin);

        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Nappi toimii");
                help = 1;

                Context context = getActivity().getApplicationContext();
                try {
                    File file = new File(context.getFilesDir(), "users.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                    DocumentBuilder db = dbf.newDocumentBuilder();

                    Document doc = db.parse(file);
                    doc.getDocumentElement().normalize();
                    System.out.println("Root element: " + doc.getDocumentElement().getNodeName());


                    NodeList nList = doc.getDocumentElement().getElementsByTagName("User");

                    for(int i = 0; i < nList.getLength(); i++) {
                        Node node = nList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;

                            if(element.getElementsByTagName("Username").item(0).getTextContent().equals(username) && element.getElementsByTagName("Password").item(0).getTextContent().equals(password)) {
                                if(help == 1) {
                                    help = 2;
                                    sendToActivity(username);
                                }

                            } else {
                                combinationCheckTextView.setText("Käyttäjänimi tai salasana on väärin.");
                            }

                        }
                    }



                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

                //sendToActivity();
            }
        });
    }

    public void sendToActivity(String un) {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.putExtra("key", un);
        startActivity(intent);
    }
}
