package gui;
import data.Trabajador;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TrabajadoresTableModel extends AbstractTableModel{
	
		private List<Trabajador> _aData;
		
		public TrabajadoresTableModel(List<Trabajador> aData) { _aData = aData; }
		
		public int getRowCount() { return _aData.size(); }
		public int getColumnCount() { return 2; }
		
		public Object getValueAt(int iRow, int iColumn) {
			
			switch(iColumn) {
				case 0: return _aData.get(iRow).getName();
				case 1: return _aData.get(iRow).getEspecie().getName();
				default: throw new IllegalArgumentException("La columna " + iColumn + " no existe.");
			}
		}
		
		public Trabajador getData(int iRow) { return _aData.get(iRow); }
}  