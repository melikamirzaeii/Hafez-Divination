import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class HafezDivinationApp {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("نیت");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        
        JButton button = new JButton("نیت");
        button.setFont(new Font("Arial", Font.BOLD, 20));
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDivination();
            }
        });

        mainFrame.getContentPane().add(button, BorderLayout.CENTER);
        
        mainFrame.setVisible(true);
    }

    private static void showDivination() {
        JFrame divinationFrame = new JFrame("فال حافظ");
        divinationFrame.setSize(500, 400);
        
        try {
            String url = "https://faal.spclashers.workers.dev/api";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(sb.toString());
            String poem = jsonResponse.getString("Poem").replace("\\r\\n", "\n");
            String interpretation = jsonResponse.getString("Interpretation").replace("\\r\\n", "\n");
            
            JLabel titleLabel = new JLabel("فال حافظ", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.BLUE);
            
            JTextArea poemArea = new JTextArea(poem);
            poemArea.setFont(new Font("Arial", Font.PLAIN, 18));
            poemArea.setLineWrap(true);
            poemArea.setWrapStyleWord(true);
            poemArea.setEditable(false);
            
            JTextArea interpretationArea = new JTextArea(interpretation);
            interpretationArea.setFont(new Font("Arial", Font.PLAIN, 16));
            interpretationArea.setLineWrap(true);
            interpretationArea.setWrapStyleWord(true);
            interpretationArea.setEditable(false);
            
            divinationFrame.setLayout(new BorderLayout());
            divinationFrame.add(titleLabel, BorderLayout.NORTH);
            divinationFrame.add(new JScrollPane(poemArea), BorderLayout.CENTER);
            divinationFrame.add(new JScrollPane(interpretationArea), BorderLayout.SOUTH);
            
            divinationFrame.setVisible(true);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
