package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_New {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);

            Translator translator = new JSONTranslator();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

            // Country Combobox Select
            JComboBox<String> languageComboBox = new JComboBox<>();

            for(String countryCode : translator.getCountryCodes()) {
                String countryName = countryCodeConverter.fromCountryCode(countryCode);
                languageComboBox.addItem(countryName);
            }
            countryPanel.add(languageComboBox);



            // Language JList Select
            JPanel languagePanel = new JPanel();
            String[] items = new String[translator.getLanguageCodes().size()-5];

            int i = 0;
            for(String langaugeCode : translator.getLanguageCodes()) {
                if (languageCodeConverter.fromLanguageCode(langaugeCode) != null) {
                    items[i++] = languageCodeConverter.fromLanguageCode(langaugeCode);
                }

            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            languagePanel.add(scrollPane);


            // Submit Button
            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            // Res
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String country = languageComboBox.getSelectedItem().toString();
                    String language = list.getSelectedValue().toString();

                    String currCountryCode = countryCodeConverter.fromCountry(country);
                    String currLanguageCode = languageCodeConverter.fromLanguage(language);

                    String translated = translator.translate(currCountryCode, currLanguageCode);
                    String result = country + " translated into " + language + " is " + translated;


                    // String result = translator.translate(country, language);
                    if (translated == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            // Put Together
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.setPreferredSize(new Dimension(400, 300));
            // Metadata
            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
