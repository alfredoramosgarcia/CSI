package gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import data.Especie;

public class EspecieListModel extends AbstractListModel<Especie> implements ComboBoxModel<Especie> {
	
	private final List<Especie> _aData;
	private Object _oSelectedObject = null;
	
	EspecieListModel(List<Especie> aData) { _aData = aData; }

	public int getSize() { return _aData.size(); }
	public Especie getElementAt(int index) { return _aData.get(index); }
	public void setSelectedItem(Object anItem) { _oSelectedObject = anItem; } 
	public Object getSelectedItem() { return _oSelectedObject; }

	
}
