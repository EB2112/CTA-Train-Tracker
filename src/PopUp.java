import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PopUp extends JDialog {

     public PopUp(JPanel jPanel, String stationName, List<Arrival> arrivals){
         super(SwingUtilities.getWindowAncestor(jPanel), stationName);

         DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[]{"Route", "Destination", "Arrives"}, 0);
         for(Arrival arrival: arrivals){
             defaultTableModel.addRow(new Object[]{arrival.route(), arrival.destination(), arrival.isApproaching() ? "Due" : arrival.arrivalTime()});
         }
         JTable jTable = new JTable(defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(jTable);

        add(scrollPane);
        setSize(500, 200);
        setLocationRelativeTo(jPanel);
        setVisible(true);
    }


}
