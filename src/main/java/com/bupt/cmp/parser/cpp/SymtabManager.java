package com.bupt.cmp.parser.cpp;


import java.util.Hashtable;
import java.util.Vector;

/**
 * Manages the symbol table and scopes within a given compilation unit.
 */
public class SymtabManager
{
   /**
    * Global symbol table indexed by the name of the scope
    * (class/function). To be deleted.
    */
   static Hashtable scopeTable = new Hashtable();

   /**
    * Stack of scopes. Currently max. nesting allowed is 100.
    */
   static Scope[] scopeStack = new Scope[100];

   /**
    * Current depth of scope nesting.
    */
   static int depth = 0;

   /**
    * Dummy at the bottom of the stack so that no need to check for null.
    */
   static 
   {
      scopeStack[depth] = new Scope(null, false);
   }
 
   /**
    * Function parameters are suspended until body scope is opened.
    */
   static Vector suspendedVarName = new Vector();
   

   /**
    * Opens a new scope (with optional name and type flag).
    */
   public static Scope OpenScope(String scopeName, boolean isType,
                                 boolean nameSpaceScope)
   {
      Scope newScope;

      if (scopeName != null)
      {
         if (isType)
         {
            newScope = new ClassScope(scopeName, scopeStack[depth], nameSpaceScope);
            scopeStack[depth].PutTypeName(scopeName, newScope);
         }
         else
         {
            newScope = new Scope(scopeName, isType, scopeStack[depth], nameSpaceScope);
         }

         scopeTable.put(scopeName, newScope);
      }
      else
         newScope = new Scope(scopeStack[depth], nameSpaceScope);

      scopeStack[++depth] = newScope;
      return newScope;
   }

   public static void OpenScope(Scope sc)
   {
      scopeStack[++depth] = sc;
   }

   public static void PutTypeName(String name)
   {
      scopeStack[depth].PutTypeName(name);
      scopeStack[1].PutTypeName(name);
   }

   public static boolean IsFullyScopedTypeName(String name)
   {
      if (name == null)
         return false;

      if (name.indexOf("::") == -1)
         return IsTypeName(name);

      Scope sc = GetScopeOfFullyScopedName(name);
      if (sc != null)
         return sc.IsTypeName(name.substring(name.lastIndexOf("::") + 2,
                                                     name.length()));
      return false;
   }

   public static boolean IsTypeName(String name)
   {
      Scope sc;

      sc = scopeStack[depth];
      while (sc != null) {
	  if (sc.IsVarName(name))
	      return false;
	  if (sc.IsTypeName(name))
	      return true;	    
	  sc = sc.parent;
      }
      return false;
   }

   public static void CloseScope()
   {
      depth--;
   }

   /**
    * For now, we just say if it is a class name, it is OK to call it a 
    * constructor.
    */
   public static boolean IsCtor(String name)
   {
      if (name == null)
         return false;

      if (name.indexOf("::") == -1)
        return GetScope(name) != null;

      Scope sc = GetScopeOfFullyScopedName(name);

      if (sc != null && sc.parent != null)
         return sc.parent.GetScope(name.substring(name.lastIndexOf("::") + 2,
                                                     name.length())) == sc;

      return false;
   }

   /**
    * It splits the parameter name into method name and type name 
    * (first parameter), using the left parenthesis as separator.
    * Then it opens the scope associated to the method name and
    * it checks whether the type name is really a type or not.
    */
   public static boolean IsMethod(String name)
    {
	String methodName, typeName;
	boolean closeReqd = false;
	boolean result = false;
	
	if (name == null || name.indexOf("(") == -1)
	    return false;
	methodName = name.substring(0, name.indexOf("("));
	typeName = name.substring(name.indexOf("(") + 1, name.length());
	Scope sc = GetScopeOfFullyScopedName(methodName);
	if (closeReqd = (sc != null && sc != GetCurScope()))
	    OpenScope(sc);
	result = IsFullyScopedTypeName(typeName);
	if (closeReqd) 
	    CloseScope();

	return result;
    }

   public static Scope GetCurScope()
   {
      return scopeStack[depth];
   }

   public static Scope GetScope(String name)
   {
      int i = depth;
      Scope sc = null;

      while (i >= 0)
         if ((sc = scopeStack[i--].GetScope(name)) != null)
            return sc;

      return null;
   }

   /**
    * Returns the Scope of B in A::B::C.
    */
  public static Scope GetScopeOfFullyScopedName(String name)
    {
      Scope sc;
      int i = 0, j = 0;

      if (name.indexOf("::") == -1)
	return GetScope(name);
      
      sc = scopeStack[1]; 
      if (name.indexOf("::") == 0)
	if (name.lastIndexOf("::") == 0)
	  return sc;
	else
	  j = 2;

      name = name.substring(j, name.lastIndexOf("::"));
      sc = GetCurScope();
      while (sc != null && (j = name.indexOf("::", i)) != -1) {
	sc = sc.GetScope(name.substring(i, j));
	if (sc == null && i == 0)
	  sc = GetScope(name.substring(i, j));
	i = j + 2;
	if (sc == null)
	  return null;
      }
     
      if (sc == GetCurScope())
	return GetScope(name.substring(i, name.length()));
      return sc.GetScope(name.substring(i, name.length()));
    }

  public static boolean IsGlobalScope()
   {
      return depth == 1 || depth == 2;
   }

  
  public static int getDepth()
   {
      return depth;
   }

  /**
   * When encountering parameter names, they are put into a suspended
   * list until scope is opened.
   */
   public static void AddSuspendedVarName(String name)
   {
      suspendedVarName.addElement(name);
   }

  /**
   * Removes all parameter names.
   */
  public static void ClearSuspendedVarName()
   {
      suspendedVarName.removeAllElements();
   }

  /**
   * Transfers parameter names into proper scope.
   */
  public static void PutSuspendedVarName()
   {
      Scope sc = GetCurScope();
      for (int i = 0; i < suspendedVarName.size(); i++)
	  sc.PutVarName((String)suspendedVarName.elementAt(i)); 
   }
  
}

