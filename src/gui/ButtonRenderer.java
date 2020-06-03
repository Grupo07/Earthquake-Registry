
package gui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * Button cell renderer to make the JTable cells of a column button look.
 * 
 * @author Luis Mariano Ramírez Segura
*/
class ButtonRenderer extends JButton implements TableCellRenderer {
    
        /**
         * This constructor set the cell visible.
         */
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        /**
         * Sets the button cell style.
         * 
         * @param table parent table
         * @param value cell value
         * @param isSelected is the cell selected
         * @param hasFocus is the cell on focus
         * @param row cell's row
         * @param column cell's column
         * @return 
         */
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
