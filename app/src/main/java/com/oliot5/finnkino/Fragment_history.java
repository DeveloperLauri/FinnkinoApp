package com.oliot5.finnkino;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Fragment_history extends Fragment {

    View view;
    ListView listView;
    Button getHistory;
    ArrayList<HistoryInfo> historyInfoArrayList = new ArrayList<HistoryInfo>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_history, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getHistory = (Button) this.view.findViewById(R.id.searchHistoryButton);
        listView = (ListView) this.view.findViewById(R.id.clickedMoviesListview);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Nappi toimii");
                HistoryLibrary hLibrary = new HistoryLibrary();

                String[] movieNameList = new String[0];
                Context context = getActivity().getApplicationContext();
                try {
                    File file = new File(context.getFilesDir(), "user1.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                    DocumentBuilder db = dbf.newDocumentBuilder();

                    Document doc = db.parse(file);
                    doc.getDocumentElement().normalize();
                    System.out.println("Root element: " + doc.getDocumentElement().getNodeName());


                    NodeList nList = doc.getDocumentElement().getElementsByTagName("movie");

                    for(int i = 0; i < nList.getLength(); i++) {
                        Node node = nList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            hLibrary.AddToList(element.getElementsByTagName("MovieName").item(0).getTextContent(), element.getElementsByTagName("stars").item(0).getTextContent());
                        }
                    }

                    historyInfoArrayList = hLibrary.getList();


                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, historyInfoArrayList);
                listView.setAdapter(aa);
            }
        };
        getHistory.setOnClickListener(listener);


    }
}
