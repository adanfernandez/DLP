package generacionDeCodigo;

import ast.*;
import visitor.*;

/** 
 * Clase encargada de asignar direcciones a las variables 
 */
public class GestionDeMemoria extends DefaultVisitor {

	private int offsetLocal = 0;
	private int offsetGlobal = 0;
	private int offsetParam = 0;
	private int offsetCampo = 0;
	
	public Object visit(DefinicionFuncion node, Object param)
	{
		node.getTipo().accept(this, param);
		
		for(DefinicionVariable var : node.getDefiniciones())
			var.accept(this, null);
		//reseteamos el offset de las variables locales.
		this.offsetLocal = 0;
		
		
		for(int i=node.getParametros().size()-1; i>=0; i--)
			node.getParametros().get(i).accept(this, null);
		//reseteamos el offset de los parámetros.
		this.offsetParam = 0;
		return null;
	}
	
	
	
	public Object visit(DefinicionEstructura node, Object param)
	{
		for(VariableEstructura var : node.getVariablesEstructura())
		{
			var.accept(this, null);
			var.setOffset(offsetCampo);
			offsetCampo += var.getTipo().getBytes();
		}
		//reseteamos el offset de los campos de las estructuras.
		this.offsetCampo = 0;
		return null;
	}
	
	public Object visit(Parametro node, Object param)
	{
		node.setOffset(offsetParam + 4);
		offsetParam += node.getTipo().getBytes();
//		System.out.println("Offset del parámetro --" + node.getNombre() + " (" + node.getTipo().getClass() +")-- es: " + node.getOffset());
		return null;
	}
	
	public Object visit(DefinicionVariable node, Object param)
	{
		node.getTipo().accept(this, null);	
		if(node.getAmbito() == 1)			// GLOBAL
		{
			node.setOffset(offsetGlobal);
			offsetGlobal += node.getTipo().getBytes();
//			System.out.println("Offset de la variable global --" + node.getNombre() + " (" + node.getTipo().getClass() +")-- es: " + node.getOffset());
		}
		else if(node.getAmbito() == 2)		// LOCAL
		{
			node.setOffset( -(offsetLocal + node.getTipo().getBytes()) );
			offsetLocal = node.getOffset() * (-1);
//			System.out.println("Offset de la variable local --" + node.getNombre() + " (" + node.getTipo().getClass() +")-- es: " + node.getOffset());
		}
		
		return null;
	}
}
