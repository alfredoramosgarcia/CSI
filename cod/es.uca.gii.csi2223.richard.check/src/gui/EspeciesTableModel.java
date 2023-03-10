package gui;
import data.Especie;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EspeciesTableModel extends AbstractTableModel{
	
		private List<Especie> _aData;
		
		public EspeciesTableModel(List<Especie> aData) { _aData = aData; }
		
		public int getRowCount() { return _aData.size(); }
		public int getColumnCount() { return 1; }
		
		public Object getValueAt(int iRow, int iColumn) {
			
			switch(iColumn) {
				case 0: return _aData.get(iRow).getName();
				default: throw new IllegalArgumentException("La columna " + iColumn + " no existe.");
			}
		}
		
		public Especie getData(int iRow) { return _aData.get(iRow); }
} 