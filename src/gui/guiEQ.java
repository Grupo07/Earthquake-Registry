
package gui;

import data.Data;
import data.Earthquake;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import map.CoordinateMap;

/**
 *
 * @author Ragnarok
 *         Luis Mariano Ramírez Segura
 * 
 */
public class guiEQ extends javax.swing.JFrame {
    
    private Data data;
    private DefaultTableModel tableModel;
    
    private DefaultTableModel tablRegistry = new DefaultTableModel();
    ArrayList<Earthquake> listEQs = new ArrayList<>();
    
    
    public guiEQ() {
        initComponents();
        
        // Center window on screen upon creation
        this.setLocationRelativeTo(null);
        
        this.data = new Data();
        this.tableModel = (DefaultTableModel) this.eTable.getModel();
        
        // Sets a button-like look to the edit/delete columns
        eTable.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        eTable.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        eTable.getColumn("Mapa").setCellRenderer(new ButtonRenderer());
        
        updateRows();
        
        setTableActionListener();
        
    }
    
    /**
     * Button cell renderer, makes the cells items of the column look like buttons.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                       boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    /**
     * Reloads the last earthquake data to the table rows.
     */
    private void updateRows() {
        tableModel.setRowCount(0);
        ArrayList<Earthquake> earthquakes = data.getAll();
        for (Earthquake earthquake : earthquakes) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
            String date = earthquake.getDate().format(dateFormat);
            String time = earthquake.getDate().format(timeFormat);
            String province = earthquake.getProvince().toString();
            String latitude = String.valueOf(earthquake.getLat());
            String longitude = String.valueOf(earthquake.getLon());
            String depth = String.valueOf(earthquake.getDepth());
            String magnitude = String.valueOf(earthquake.getMagnitude());
            String magnitudeType = earthquake.getMagnitudeType().toString();
            String failureOrigin = earthquake.getOriginFailure().toString();
            String details = earthquake.getDetails();
            
                    
            tableModel.addRow(new Object[]{date, time, province, latitude, longitude, 
                                      depth, magnitude, magnitudeType, failureOrigin, details, 
                                      "Ver", "Editar", "Eliminar"});
        }
    }
    
    /**
     * Sets the table click listener for edit/delete/map
     * earthquake action detection.
     */
    private void setTableActionListener() {
        eTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = eTable.rowAtPoint(evt.getPoint());
                int column = eTable.columnAtPoint(evt.getPoint());
                
                int mapColumn = 10;
                int editColumn = 11;
                int removeColumn = 12;
                
                if (column == mapColumn) {
                    new CoordinateMap(data.getAll().get(row).getLat(), data.getAll().get(row).getLon()).display();
                }

                
                if (column == editColumn) {
                    
                }
                
                if (column == removeColumn) {
                    deleteRowAt(row);
                }
                
                if (column == mapColumn) {
                    
                }

            }
        });
    }
    
    private void deleteRowAt(int rowIndex) {
        int deleteResponse = JOptionPane.showConfirmDialog(null, "Seguro que quieres eliminar este sismo?");
        int deleteConfirmed = 0;
        if (deleteResponse == deleteConfirmed) {
        int earthquakeId = data.getAll().get(rowIndex).getId();
        try {
            
            data.deleteEarthquake(earthquakeId);
            updateRows();
            
        } catch (IOException e) {
            Logger.getLogger(guiEQ.class.getName()).log(Level.SEVERE, null, e);
        }
        }
    }
    
    
    private void addEQtxtfield(){
        //Get data from txtFields
//        String province = txtProvince.getText();
//        String date = txtDate.getText();
//        String depth = txtDepth.getText();
//        String lat = txtLat.getText();
//        String lon = txtLon.getText();
//        String epicenter = txtEpicenter.getText();
//        String details = txtDetails.getText();
//        String magnitude = txtMagnitude.getText();
        //int id = generateId();
        
        //Turn data gotten from txtFielfs into its correpondent values
        /*addEarthquake(Province province, LocalDateTime date, 
            float depth, double lat, double lon, 
            FaultOrigin originFailure, String details, float magnitude)
        Earthquake newData = new Earthquake(id,province,date,depth,lat,
                lon,originFailure,details,magnitude);
        data.add(newData);
        saveFile();*/
       // Earthquake newdata = new Earthquake(id, )
        
        
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        RegistryPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JScrollPane();
        eTable = new javax.swing.JTable();
        InputPanel = new javax.swing.JPanel();
        dateInput = new com.toedter.calendar.JDateChooser();
        hourInput = new javax.swing.JSpinner();
        minuteInput = new javax.swing.JSpinner();
        secondInput = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        provinceInput = new javax.swing.JComboBox<>();
        originInput = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        depthInput = new javax.swing.JSpinner();
        latitudeInput = new javax.swing.JSpinner();
        longitudeInput = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        magnitudeInput = new javax.swing.JSpinner();
        descriptionInput = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        cancelBtn = new javax.swing.JButton();
        actionBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Bienvenidos al Registro de Sismos de Costa Rica");

        eTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Hora", "Provincia", "Latitud", "Longitud", "Profundidad", "Magnitud", "Clasificación", "Origen", "Descripción", "Mapa", "Editar", "Eliminar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePanel.setViewportView(eTable);
        if (eTable.getColumnModel().getColumnCount() > 0) {
            eTable.getColumnModel().getColumn(8).setPreferredWidth(230);
        }

        dateInput.setDateFormatString("d-MMM-yyyy");

        hourInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));

        minuteInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        secondInput.setModel(new javax.swing.SpinnerNumberModel(1, 0, 59, 1));

        jLabel2.setText("Hora");

        jLabel3.setText("Minuto");

        jLabel4.setText("Segundo");

        jLabel5.setText("Latitud");

        jLabel6.setText("Longitud");

        provinceInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SAN_JOSE", "GUANACASTE", "ALAJUELA", "HEREDIA", "CARTAGO", "LIMON", "PUNTARENAS", "MAR" }));

        originInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SUBDUCCION_DE_PLACA", "CHOQUE_DE_PLACAS", "TECTONICO_POR_SUBDUCCION", "DEFORMACION_INTERNA", "FALLAMIENTO_LOCAL", "TECTONICO_POR_FALLA_LOCAL" }));

        jLabel7.setText("Profundidad");

        depthInput.setModel(new javax.swing.SpinnerNumberModel(0.1f, 0.1f, null, 1.0f));

        latitudeInput.setModel(new javax.swing.SpinnerNumberModel(9.5d, null, null, 0.1d));

        longitudeInput.setModel(new javax.swing.SpinnerNumberModel(-84.5d, null, null, 0.1d));

        jLabel8.setText("Magnitud");

        magnitudeInput.setModel(new javax.swing.SpinnerNumberModel(0.1f, 0.1f, null, 1.0f));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Descripción\n");
        descriptionInput.setViewportView(jTextArea1);

        cancelBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cancelBtn.setText("Cancelar");

        actionBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        actionBtn.setText("Agregar");
        actionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InputPanelLayout = new javax.swing.GroupLayout(InputPanel);
        InputPanel.setLayout(InputPanelLayout);
        InputPanelLayout.setHorizontalGroup(
            InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(InputPanelLayout.createSequentialGroup()
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(hourInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(minuteInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(secondInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(dateInput, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(InputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(193, 193, 193)
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(depthInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(magnitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(InputPanelLayout.createSequentialGroup()
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(provinceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(InputPanelLayout.createSequentialGroup()
                                .addComponent(latitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(longitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(25, 25, 25)
                        .addComponent(originInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(descriptionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        InputPanelLayout.setVerticalGroup(
            InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputPanelLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InputPanelLayout.createSequentialGroup()
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(descriptionInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(InputPanelLayout.createSequentialGroup()
                                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(provinceInput)
                                        .addComponent(originInput))
                                    .addComponent(dateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(depthInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(longitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(latitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(magnitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(secondInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(hourInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(minuteInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(InputPanelLayout.createSequentialGroup()
                        .addGroup(InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(actionBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))))
        );

        javax.swing.GroupLayout RegistryPanelLayout = new javax.swing.GroupLayout(RegistryPanel);
        RegistryPanel.setLayout(RegistryPanelLayout);
        RegistryPanelLayout.setHorizontalGroup(
            RegistryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel)
            .addGroup(RegistryPanelLayout.createSequentialGroup()
                .addComponent(InputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        RegistryPanelLayout.setVerticalGroup(
            RegistryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegistryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Registro", RegistryPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1139, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Estadísticas", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(315, 315, 315)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actionBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(guiEQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(guiEQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(guiEQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(guiEQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new guiEQ().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InputPanel;
    private javax.swing.JPanel RegistryPanel;
    private javax.swing.JButton actionBtn;
    private javax.swing.JButton cancelBtn;
    private com.toedter.calendar.JDateChooser dateInput;
    private javax.swing.JSpinner depthInput;
    private javax.swing.JScrollPane descriptionInput;
    private javax.swing.JTable eTable;
    private javax.swing.JSpinner hourInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JSpinner latitudeInput;
    private javax.swing.JSpinner longitudeInput;
    private javax.swing.JSpinner magnitudeInput;
    private javax.swing.JSpinner minuteInput;
    private javax.swing.JComboBox<String> originInput;
    private javax.swing.JComboBox<String> provinceInput;
    private javax.swing.JSpinner secondInput;
    private javax.swing.JScrollPane tablePanel;
    // End of variables declaration//GEN-END:variables
}
