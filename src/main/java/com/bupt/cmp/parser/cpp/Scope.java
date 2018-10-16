package com.bupt.cmp.parser.cpp;

/*******************************************************************************
 * Author : Sreenivasa R. Viswanadha <sreeni@metamata.com>
 * Modified version: there was a bug in TypeTable.
 * Modificatio made by: Ferenc Magyar <magyar@inf.u-szeged.hu>
 *			MTA-JATE Research Group on Artificial Intelligence
 *			11/20/97
 *
 * Last change:  M    19 Oct 97    4:27 pm
*******************************************************************************/


import java.util.Hashtable;
import java.util.Vector;

public class Scope
{
   /**
    * The list of scopes corresponding to namespaces this class uses.
    */
   Vector usedNamespaces;

   /**
    * Name of the scope (set only for class/function scopes).
	Last change:  M    19 Oct 97    4:21 pm
    */
   public String scopeName;


   /**
    *  Indicates whether this is a namespace scope or not.
    */
   public boolean isANameSpaceScope;

   /**
    * Indicates whether this is a class scope or not.
    */
   boolean type;     // Indicates if this is a type.

   /**
    * (partial) table of type symbols introduced in this scope.
    */
   Hashtable typeTable = new Hashtable();

   /**
    * (partial) table of variables declared in this scope.
    */
   Hashtable varTable = new Hashtable();

   /**
    * Parent scope. (null if it is the global scope).
    */
   Scope parent;

   /**
    * Creates a scope object with a given name.
    */
   public Scope(String name, boolean isType, Scope p, boolean nameSpaceScope)
   {
      usedNamespaces = new Vector();
      scopeName = name;
      type = isType;
      parent = p;
      isANameSpaceScope = nameSpaceScope;
   }

   /**
    * Creates an unnamed scope (like for compound statements).
    */
   public Scope(Scope p, boolean nameSpaceScope)
   {
      usedNamespaces = new Vector();
      type = false;
      parent = p;
      isANameSpaceScope = nameSpaceScope;
   }

   /**
    * Inserts a name into the table to say that it is the name of a type.
    * There was a bug. The simple type declarartion override the existing type
    * definition.
    * Correction made: 11/20/97 by Ferenc Magyar <magyar@inf.u-szeged.hu>
    */
   public void PutTypeName(String name)
   {
     if (typeTable.get(name)==null )
        typeTable.put(name, name);
   }

   /**
    * Inserts a name into the table to say that it is the name of a  variable 
    * and therefore it is NOT the name of a type, thus overriding any outer 
    * type definition with the same name.
    */
   public void PutVarName(String name)
   {
     if (varTable.get(name)==null )
        varTable.put(name, name);
   }

   /**
    * A type with a scope (class/struct/union).
    */
   public void PutTypeName(String name, Scope sc)
   {
      typeTable.put(name, sc);
   }

   /**
    * Add a namespace.
    */
   public void AddNamespace(Scope sc)
   {
     if (sc == null || sc == this) 
	  return;                   
      if (usedNamespaces == null)
         usedNamespaces = new Vector();

      usedNamespaces.addElement(sc);
   }

   /** 
    * Checks if a given name is the name of a type in this scope.
    */
   public boolean IsTypeName(String name)
   {
      if (varTable.get(name) != null)
         return false;

      if (typeTable.get(name) != null)
         return true;

      for (int i = 0; i < usedNamespaces.size(); i++) {
	if (((Scope)usedNamespaces.elementAt(i)).IsTypeName(name))	 
	  return true;
      }
      return false;
   }

   /** 
    * Checks if a given name is the name of a variable in this scope.
    */
   public boolean IsVarName(String name)
   {
      return (varTable.get(name) != null);
   }

   public Scope GetScope(String name)
   {
      Object sc = typeTable.get(name);
      if (sc == null)
	for (int i = 0; sc == null && i < usedNamespaces.size(); i++)
	  sc = ((Scope)usedNamespaces.elementAt(i)).GetScope(name);

      if (sc instanceof Scope || sc instanceof ClassScope)
         return (Scope)sc;

      return null;
   }
}
