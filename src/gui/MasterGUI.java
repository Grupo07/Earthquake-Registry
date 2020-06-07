
package gui;

import charts.BarChart;
import charts.DateRangeTable;
import charts.HistogramChart;
import charts.PieChart;
import data.Data;
import data.Earthquake;
import data.FaultOrigin;
import data.Province;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import map.CoordinateMap;

/**
 * Main app gui, contains two tabs: earthquake registry and statistics.
 * In the registry the earthquakes can be added, updated or deleted.
 * In the statistics tab 4 charts can be selected: a pie chart, 
 * bar chart, histogram and a tabular chart.
 * 
 * @author Luis Mariano Ramírez Segura
 * 
 */
public class MasterGUI extends javax.swing.JFrame {
    
    /**
     * Earthquakes interface. 
     * Used for the data file manipulation.
     */
    private Data data;
    
    /**
     * Table interface. 
     * Used for table manipulation.
     */
    private DefaultTableModel tableModel;
    
    /**
     * Indicates if add or edit earthquake mode is active.
     */
    private int rowToEdit;
    
    /**
     * This constructor initializes the window and table basic configurations.
     */
    public MasterGUI() {
        initComponents();
        
        // Center window on screen upon creation
        this.setLocationRelativeTo(null);
        
        // Registry Tab Setup
        this.data = new Data();
        this.tableModel = (DefaultTableModel) this.eTable.getModel();
        
        // Set current date as default value
        this.dateInput.setDate(new Date());
        
        // Hide the cancel button, show only in edit earthquake mode
        this.cancelBtn.setVisible(false);
        
        // When in non edit mode rowToEdit is -1
        this.rowToEdit = -1;
        
        // Sets a button-like look to the edit/delete columns
        eTable.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        eTable.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        eTable.getColumn("Mapa").setCellRenderer(new ButtonRenderer());
        
        // Update the earthquakes rows to the last values
        updateRows();
        
        setTableActionListener();
        
        
        // Charts Tab Setup
        
        // hides chart frame options button
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.chartFrame.getUI()).setNorthPane(null);
        
        // default chart
        this.chartFrame.setContentPane(new PieChart(this.data.getAll()).getPanel());
        
        this.barChartYear.setValue(2010);
        this.startDateInputTable.setDate(new Date(0));
        this.endDateInputTable.setDate(new Date());
        
        hideAllChartInputs();
        
        setupChartInputsListeners();
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
            String details = earthquake.getDetails().replace(";", ",");
            
                    
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
                    rowToEdit = row;
                    actionBtn.setText("Actualizar");
                    cancelBtn.setVisible(true);
                    setValuesToEdit();
                }
                
                if (column == removeColumn) {
                    deleteRowAt(row);
                }

            }
        });
    }
    
    /**
     * Deletes a table row and updates the table.
     * 
     * @param rowIndex row index to be deleted
     */
    private void deleteRowAt(int rowIndex) {
        int deleteResponse = JOptionPane.showConfirmDialog(null, "Seguro que quieres eliminar este sismo?");
        int deleteConfirmed = 0;
        if (deleteResponse == deleteConfirmed) {
        int earthquakeId = data.getAll().get(rowIndex).getId();
        try {
            
            data.deleteEarthquake(earthquakeId);
            updateRows();
            
        } catch (IOException e) {
            Logger.getLogger(MasterGUI.class.getName()).log(Level.SEVERE, null, e);
        }
        }
    }
    
    /**
     * Sets in the earthquake form the values to be updated.
     */
    private void setValuesToEdit() {
        Earthquake toEdit = data.getAll().get(this.rowToEdit);
        LocalDateTime date = toEdit.getDate();
        this.dateInput.setDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
        this.hourInput.setValue(date.getHour());
        this.minuteInput.setValue(date.getMinute());
        this.secondsInput.setValue(date.getSecond());
        this.provinceInput.setSelectedItem(toEdit.getProvince().toString());
        this.latitudeInput.setValue(toEdit.getLat());
        this.longitudeInput.setValue(toEdit.getLon());
        this.originInput.setSelectedItem(toEdit.getOriginFailure().toString());
        this.depthInput.setValue(toEdit.getDepth());
        this.magnitudeInput.setValue(toEdit.getMagnitude());
        String description = toEdit.getDetails().replace(";", ",");
        this.descriptionInput.setText(description);
    }
    
    /**
     * Validates the date and time inserted by the user.
     * Shows warning messages if data is not valid.
     * 
     * @param date date inserted
     * @param hour hour inserted
     * @param minute minute inserted
     * @param seconds seconds inserted
     * @param description earthquake description inserted
     * @return data validity
     */
    private boolean dataIsValid(Date date, int hour, int minute, int seconds, String description) {
        if (date == null) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una fecha");
            return false;
        }
        
        if (hour == 0 && minute == 0 && seconds == 0) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una hora, minuto o segundo distinto a cero");
            return false;
        }
        
        description = description.replaceAll("\\r\\n|\\r|\\n", "");
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una descripción");
            return false;
        }
        
        return true;
    }
    
    /**
     * Sets up all charts inputs action listeners
     */
    private void setupChartInputsListeners() {
        
        this.barChartYear.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                int year = (int) barChartYear.getValue();
                chartFrame.setContentPane(new BarChart(data.getAll(), year).getPanel());
            }
        });
        
        this.histogramProvince.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = histogramProvince.getSelectedItem().toString();
                if (selection == "TODOS") {
                    chartFrame.setContentPane(new HistogramChart(data.getAll()).getPanel());
                } else {
                    Province province = Province.valueOf(selection);
                    chartFrame.setContentPane(new HistogramChart(data.getAll(), province).getPanel());
                }
            }
        });
        
        PropertyChangeListener rangeDateListener = (PropertyChangeEvent pce) -> {
            loadTableChart();
        };
        
        this.startDateInputTable.getDateEditor().addPropertyChangeListener(rangeDateListener);
        this.endDateInputTable.getDateEditor().addPropertyChangeListener(rangeDateListener);
        
    }
    
    /**
     * Hides all of the chart data inputs
     */
    private void hideAllChartInputs() {
        this.barChartYear.setVisible(false);
        this.histogramProvince.setVisible(false);
        this.startDateInputTable.setVisible(false);
        this.endDateInputTable.setVisible(false);
        this.startLabel.setVisible(false);
        this.endLabel.setVisible(false);
    }
    
    /**
     * Loads the date range table to the statistics tab.
     */
    private void loadTableChart() {
        Date dateStart = this.startDateInputTable.getDate();
        Date dateEnd = this.endDateInputTable.getDate();
        if (dateStart != null && dateEnd != null) {
            LocalDateTime start = LocalDateTime.ofInstant(dateStart.toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime end = LocalDateTime.ofInstant(dateEnd.toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0).withNano(0);
            chartFrame.setContentPane(new DateRangeTable(data.getAll(), start, end).getPanel());
            hideAllChartInputs();
            this.startDateInputTable.setVisible(true);
            this.endDateInputTable.setVisible(true);
            this.startLabel.setVisible(true);
            this.endLabel.setVisible(true);
        }
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
        registryPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JScrollPane();
        eTable = new javax.swing.JTable();
        inputPanel = new javax.swing.JPanel();
        hourInput = new javax.swing.JSpinner();
        minuteInput = new javax.swing.JSpinner();
        secondsInput = new javax.swing.JSpinner();
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
        descriptionPanel = new javax.swing.JScrollPane();
        descriptionInput = new javax.swing.JTextArea();
        cancelBtn = new javax.swing.JButton();
        actionBtn = new javax.swing.JButton();
        dateInput = new com.toedter.calendar.JDateChooser();
        statisticsPanel = new javax.swing.JPanel();
        chartFrame = new javax.swing.JInternalFrame();
        pieChartBtn = new javax.swing.JButton();
        barChartBtn = new javax.swing.JButton();
        barChartYear = new javax.swing.JSpinner();
        histogramBtn = new javax.swing.JButton();
        histogramProvince = new javax.swing.JComboBox<>();
        dateRangeChartBtn = new javax.swing.JButton();
        startDateInputTable = new com.toedter.calendar.JDateChooser();
        endDateInputTable = new com.toedter.calendar.JDateChooser();
        endLabel = new javax.swing.JLabel();
        startLabel = new javax.swing.JLabel();

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

        hourInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));

        minuteInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        secondsInput.setModel(new javax.swing.SpinnerNumberModel(1, 0, 59, 1));

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

        descriptionInput.setColumns(20);
        descriptionInput.setLineWrap(true);
        descriptionInput.setRows(5);
        descriptionInput.setText("Descripción\n");
        descriptionPanel.setViewportView(descriptionInput);

        cancelBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cancelBtn.setText("Cancelar");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        actionBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        actionBtn.setText("Agregar");
        actionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(hourInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(minuteInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(secondsInput, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(dateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(193, 193, 193)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(depthInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(magnitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(provinceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(inputPanelLayout.createSequentialGroup()
                                .addComponent(latitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(longitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(25, 25, 25)
                        .addComponent(originInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(inputPanelLayout.createSequentialGroup()
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(originInput, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(provinceInput)
                                    .addComponent(dateInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(depthInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(longitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(latitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(magnitudeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(secondsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(hourInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(minuteInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(actionBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))))
        );

        javax.swing.GroupLayout registryPanelLayout = new javax.swing.GroupLayout(registryPanel);
        registryPanel.setLayout(registryPanelLayout);
        registryPanelLayout.setHorizontalGroup(
            registryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tablePanel)
            .addGroup(registryPanelLayout.createSequentialGroup()
                .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        registryPanelLayout.setVerticalGroup(
            registryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Registro", registryPanel);

        chartFrame.setResizable(true);
        chartFrame.setPreferredSize(new java.awt.Dimension(1000, 650));
        chartFrame.setVisible(true);

        javax.swing.GroupLayout chartFrameLayout = new javax.swing.GroupLayout(chartFrame.getContentPane());
        chartFrame.getContentPane().setLayout(chartFrameLayout);
        chartFrameLayout.setHorizontalGroup(
            chartFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 990, Short.MAX_VALUE)
        );
        chartFrameLayout.setVerticalGroup(
            chartFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 686, Short.MAX_VALUE)
        );

        pieChartBtn.setText("Por Origen");
        pieChartBtn.setPreferredSize(new java.awt.Dimension(85, 85));
        pieChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pieChartBtnActionPerformed(evt);
            }
        });

        barChartBtn.setText("Cantidad mensual en un año");
        barChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barChartBtnActionPerformed(evt);
            }
        });

        barChartYear.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        histogramBtn.setText("Por rangos de magnitud");
        histogramBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                histogramBtnActionPerformed(evt);
            }
        });

        histogramProvince.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "SAN_JOSE", "GUANACASTE", "ALAJUELA", "HEREDIA", "CARTAGO", "LIMON", "PUNTARENAS", "MAR" }));

        dateRangeChartBtn.setText("Por rango de fechas");
        dateRangeChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateRangeChartBtnActionPerformed(evt);
            }
        });

        endLabel.setText("Fin");

        startLabel.setText(" Inicio");

        javax.swing.GroupLayout statisticsPanelLayout = new javax.swing.GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chartFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(statisticsPanelLayout.createSequentialGroup()
                        .addComponent(pieChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barChartYear, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(histogramBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(histogramProvince, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateRangeChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(statisticsPanelLayout.createSequentialGroup()
                                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(endDateInputTable, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(startDateInputTable, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(startLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(endLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dateRangeChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(histogramBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pieChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(barChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statisticsPanelLayout.createSequentialGroup()
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(barChartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(histogramProvince, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51))
                    .addGroup(statisticsPanelLayout.createSequentialGroup()
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDateInputTable, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startDateInputTable, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(startLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Estadísticas", statisticsPanel);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Adds or updates the earthquake given in the data form.
     * 
     * @param evt button clicked event
     */
    private void actionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnActionPerformed
        int hour = (int) this.hourInput.getValue();
        int minute = (int) this.minuteInput.getValue();
        int seconds = (int) this.secondsInput.getValue();
        Date date = this.dateInput.getDate();
        String description = this.descriptionInput.getText().replace(",", ";");
        
        if (dataIsValid(date, hour, minute, seconds, description)) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(this.dateInput.getDate().toInstant(), ZoneId.systemDefault())
                             .withHour(hour).withMinute(minute).withSecond(seconds).withNano(0);
            Province province = Province.valueOf(this.provinceInput.getSelectedItem().toString());
            double latitude = (double) this.latitudeInput.getValue();
            double longitude = (double) this.longitudeInput.getValue();
            FaultOrigin faultOrigin = FaultOrigin.valueOf(this.originInput.getSelectedItem().toString());
            float depth = (float) this.depthInput.getValue();
            float magnitude = (float) this.magnitudeInput.getValue();
            
            boolean inEditMode = (this.rowToEdit != -1);
            if (inEditMode) {
                int earthquakeId = data.getAll().get(this.rowToEdit).getId();
                Earthquake updatedEarthquake = new Earthquake(earthquakeId, province, dateTime, 
                                                              depth, latitude, longitude, faultOrigin, 
                                                              description, magnitude);
                try {
                    this.data.updateEarthquake(earthquakeId, updatedEarthquake);
                } catch (IOException ex) {
                    Logger.getLogger(MasterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                actionBtn.setText("Agregar");
                cancelBtn.setVisible(false);

            }else {
                try {
                    this.data.addEarthquake(province, dateTime, depth, latitude, longitude, faultOrigin, description, magnitude);
                    
                } catch (IOException ex) {
                    Logger.getLogger(MasterGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            updateRows();
        }
    }//GEN-LAST:event_actionBtnActionPerformed

    /**
     * Cancels the edit earthquake mode.
     * 
     * @param evt button clicked event
     */
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.rowToEdit = -1;
        this.actionBtn.setText("Agregar");
        this.cancelBtn.setVisible(false);
    }//GEN-LAST:event_cancelBtnActionPerformed

    /**
     * Shows the pie chart in the chart panel.
     * 
     * @param evt button clicked event
     */
    private void pieChartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pieChartBtnActionPerformed
        this.chartFrame.setContentPane(new PieChart(this.data.getAll()).getPanel());
        hideAllChartInputs();
    }//GEN-LAST:event_pieChartBtnActionPerformed

    /**
     * Shows the bar chart in the chart panel.
     * 
     * @param evt button clicked event
     */
    private void barChartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barChartBtnActionPerformed
        int year = (int) barChartYear.getValue();
        chartFrame.setContentPane(new BarChart(data.getAll(), year).getPanel());
        hideAllChartInputs();
        this.barChartYear.setVisible(true);
    }//GEN-LAST:event_barChartBtnActionPerformed

    /**
     * Shows the histogram chart in the chart panel.
     * 
     * @param evt button clicked event
     */
    private void histogramBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_histogramBtnActionPerformed
        chartFrame.setContentPane(new HistogramChart(data.getAll()).getPanel());
        hideAllChartInputs();
        this.histogramProvince.setVisible(true);
    }//GEN-LAST:event_histogramBtnActionPerformed

    /**
     * Shows the table chart in the chart panel.
     * 
     * @param evt button clicked event
     */
    private void dateRangeChartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateRangeChartBtnActionPerformed
        loadTableChart();
    }//GEN-LAST:event_dateRangeChartBtnActionPerformed

    
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
            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionBtn;
    private javax.swing.JButton barChartBtn;
    private javax.swing.JSpinner barChartYear;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JInternalFrame chartFrame;
    private com.toedter.calendar.JDateChooser dateInput;
    private javax.swing.JButton dateRangeChartBtn;
    private javax.swing.JSpinner depthInput;
    private javax.swing.JTextArea descriptionInput;
    private javax.swing.JScrollPane descriptionPanel;
    private javax.swing.JTable eTable;
    private com.toedter.calendar.JDateChooser endDateInputTable;
    private javax.swing.JLabel endLabel;
    private javax.swing.JButton histogramBtn;
    private javax.swing.JComboBox<String> histogramProvince;
    private javax.swing.JSpinner hourInput;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner latitudeInput;
    private javax.swing.JSpinner longitudeInput;
    private javax.swing.JSpinner magnitudeInput;
    private javax.swing.JSpinner minuteInput;
    private javax.swing.JComboBox<String> originInput;
    private javax.swing.JButton pieChartBtn;
    private javax.swing.JComboBox<String> provinceInput;
    private javax.swing.JPanel registryPanel;
    private javax.swing.JSpinner secondsInput;
    private com.toedter.calendar.JDateChooser startDateInputTable;
    private javax.swing.JLabel startLabel;
    private javax.swing.JPanel statisticsPanel;
    private javax.swing.JScrollPane tablePanel;
    // End of variables declaration//GEN-END:variables
}
