// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;
import java.util.ArrayList;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
  protected Program(int lineNumber) {
    super(lineNumber);
  }
  public abstract void dump_with_types(PrintStream out, int n);
  public abstract void semant();
  public abstract void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c);

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
  protected Class_(int lineNumber) {
    super(lineNumber);
  }
  public abstract void dump_with_types(PrintStream out, int n);
  public abstract void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c);

}


/** Defines list phylum Classes
  <p>
  See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
  public final static Class elementClass = Class_.class;
  /** Returns class of this lists's elements */
  public Class getElementClass() {
    return elementClass;
  }
  protected Classes(int lineNumber, Vector elements) {
    super(lineNumber, elements);
  }
  /** Creates an empty "Classes" list */
  public Classes(int lineNumber) {
    super(lineNumber);
  }
  /** Appends "Class_" element to this list */
  public Classes appendElement(TreeNode elem) {
    addElement(elem);
    return this;
  }
  public TreeNode copy() {
    return new Classes(lineNumber, copyElements());
  }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
  protected Feature(int lineNumber) {
    super(lineNumber);
  }
  public abstract void dump_with_types(PrintStream out, int n);
  public abstract void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c);

}


/** Defines list phylum Features
  <p>
  See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
  public final static Class elementClass = Feature.class;
  /** Returns class of this lists's elements */
  public Class getElementClass() {
    return elementClass;
  }
  protected Features(int lineNumber, Vector elements) {
    super(lineNumber, elements);
  }
  /** Creates an empty "Features" list */
  public Features(int lineNumber) {
    super(lineNumber);
  }
  /** Appends "Feature" element to this list */
  public Features appendElement(TreeNode elem) {
    addElement(elem);
    return this;
  }
  public TreeNode copy() {
    return new Features(lineNumber, copyElements());
  }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
  protected Formal(int lineNumber) {
    super(lineNumber);
  }
  public abstract void dump_with_types(PrintStream out, int n);
  public abstract void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c);

}


/** Defines list phylum Formals
  <p>
  See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
  public final static Class elementClass = Formal.class;
  /** Returns class of this lists's elements */
  public Class getElementClass() {
    return elementClass;
  }
  protected Formals(int lineNumber, Vector elements) {
    super(lineNumber, elements);
  }
  /** Creates an empty "Formals" list */
  public Formals(int lineNumber) {
    super(lineNumber);
  }
  /** Appends "Formal" element to this list */
  public Formals appendElement(TreeNode elem) {
    addElement(elem);
    return this;
  }
  public TreeNode copy() {
    return new Formals(lineNumber, copyElements());
  }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
  protected Expression(int lineNumber) {
    super(lineNumber);
  }
  private AbstractSymbol type = null;                                 
  public AbstractSymbol get_type() { return type; }           
  public Expression set_type(AbstractSymbol s) { type = s; return this; } 
  public abstract void dump_with_types(PrintStream out, int n);
  public void dump_type(PrintStream out, int n) {
    if (type != null)
    { out.println(Utilities.pad(n) + ": " + type.getString()); }
    else
    { out.println(Utilities.pad(n) + ": _no_type"); }
  }
  public abstract void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c);

  public void assertType(AbstractSymbol child, AbstractSymbol parent, AbstractSymbol f, ClassTable m, class_c c) {
    if (!m.classIsSubclassOf(child, parent, c)) {
      m.semantError(f, this, child.getString() + " is not subtype of " + parent.getString());
    }
  }
}


/** Defines list phylum Expressions
  <p>
  See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
  public final static Class elementClass = Expression.class;
  /** Returns class of this lists's elements */
  public Class getElementClass() {
    return elementClass;
  }
  protected Expressions(int lineNumber, Vector elements) {
    super(lineNumber, elements);
  }
  /** Creates an empty "Expressions" list */
  public Expressions(int lineNumber) {
    super(lineNumber);
  }
  /** Appends "Expression" element to this list */
  public Expressions appendElement(TreeNode elem) {
    addElement(elem);
    return this;
  }
  public TreeNode copy() {
    return new Expressions(lineNumber, copyElements());
  }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
  protected Case(int lineNumber) {
    super(lineNumber);
  }
  public abstract void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c);
  public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
  <p>
  See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
  public final static Class elementClass = Case.class;
  /** Returns class of this lists's elements */
  public Class getElementClass() {
    return elementClass;
  }
  protected Cases(int lineNumber, Vector elements) {
    super(lineNumber, elements);
  }
  /** Creates an empty "Cases" list */
  public Cases(int lineNumber) {
    super(lineNumber);
  }
  /** Appends "Case" element to this list */
  public Cases appendElement(TreeNode elem) {
    addElement(elem);
    return this;
  }
  public TreeNode copy() {
    return new Cases(lineNumber, copyElements());
  }
}


/** Defines AST constructor 'programc'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
  protected Classes classes;
  /** Creates "programc" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for classes
   */
  public programc(int lineNumber, Classes a1) {
    super(lineNumber);
    classes = a1;
  }
  public TreeNode copy() {
    return new programc(lineNumber, (Classes)classes.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "programc\n");
    classes.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_program");
    for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
      // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
      ((Class_)e.nextElement()).dump_with_types(out, n + 2);
    }
  }
  /** This method is the entry point to the semantic checker.  You will
    need to complete it in programming assignment 4.
    <p>
    Your checker should do the following two things:
    <ol>
    <li>Check that the program is semantically correct
    <li>Decorate the abstract syntax tree with type information
    by setting the type field in each Expression node.
    (see tree.h)
    </ol>
    <p>
    You are free to first do (1) and make sure you catch all semantic
    errors. Part (2) can be done in a second stage when you want
    to test the complete compiler.
    */
  public void semant() {
    /* ClassTable constructor may do some semantic analysis */
    ClassTable classTable = new ClassTable(classes);

    if (classTable.errors()) {
      System.err.println("Compilation halted due to static semantic errors.");
      System.exit(1);
    }

    SymbolTable o = new SymbolTable();

    checkType(null, o, classTable, null);

    if (classTable.errors()) {
      System.err.println("Compilation halted due to static semantic errors.");
      System.exit(1);
    }
  }

  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.println("checkType programc");
    }
    for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
      // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
      class_c cls = ((class_c)e.nextElement());
      cls.checkType(f, o, m, cls);
    }
  }

}


/** Defines AST constructor 'class_c'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
  protected AbstractSymbol name;
  protected AbstractSymbol parent;
  public Features features;
  protected AbstractSymbol filename;
  /** Creates "class_c" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   * @param a1 initial value for parent
   * @param a2 initial value for features
   * @param a3 initial value for filename
   */
  public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
    super(lineNumber);
    name = a1;
    parent = a2;
    features = a3;
    filename = a4;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    o.enterScope();
    o.addId(TreeConstants.self, TreeConstants.SELF_TYPE);
    for (Enumeration e = features.getElements(); e.hasMoreElements();) {
      Feature fe = ((Feature)e.nextElement());
      fe.checkType(filename, o, m, c);
    }
    o.exitScope();
  }
  public TreeNode copy() {
    return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "class_c\n");
    dump_AbstractSymbol(out, n+2, name);
    dump_AbstractSymbol(out, n+2, parent);
    features.dump(out, n+2);
    dump_AbstractSymbol(out, n+2, filename);
  }


  public AbstractSymbol getFilename() { return filename; }
  public AbstractSymbol getName()     { return name; }
  public AbstractSymbol getParent()   { return parent; }

  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_class");
    dump_AbstractSymbol(out, n + 2, name);
    dump_AbstractSymbol(out, n + 2, parent);
    out.print(Utilities.pad(n + 2) + "\"");
    Utilities.printEscapedString(out, filename.getString());
    out.println("\"\n" + Utilities.pad(n + 2) + "(");
    for (Enumeration e = features.getElements(); e.hasMoreElements();) {
      ((Feature)e.nextElement()).dump_with_types(out, n + 2);
    }
    out.println(Utilities.pad(n + 2) + ")");
  }

}


/** Defines AST constructor 'method'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
  public AbstractSymbol name;
  public Formals formals;
  public AbstractSymbol return_type;
  protected Expression expr;
  /** Creates "method" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   * @param a1 initial value for formals
   * @param a2 initial value for return_type
   * @param a3 initial value for expr
   */
  public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
    super(lineNumber);
    name = a1;
    formals = a2;
    return_type = a3;
    expr = a4;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    o.enterScope();
    for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
      Formal fe = ((Formal)e.nextElement());
      fe.checkType(f, o, m, c);
    }
    AbstractSymbol t0 = return_type;
    if (t0.equals(TreeConstants.SELF_TYPE)) {
      t0 = c.getName();
    }
    expr.checkType(f, o, m, c);
    if (!m.classIsSubclassOf(expr.get_type(), t0, c)) {
      //expr.dump_with_types(m.semantError(), 0);
      try {
        m.semantError(f, this, "method " + c.getName().getString() + "." + name.getString() +  " return type error: " + expr.get_type().getString() + " is not subtype of " + t0.getString());
      } catch (Exception e) {
        m.semantError(f, this, "method return type error: " + t0.getString());
      }
    }
    o.exitScope();
  }
  public TreeNode copy() {
    return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "method\n");
    dump_AbstractSymbol(out, n+2, name);
    formals.dump(out, n+2);
    dump_AbstractSymbol(out, n+2, return_type);
    expr.dump(out, n+2);
  }

  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_method");
    dump_AbstractSymbol(out, n + 2, name);
    for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
      ((Formal)e.nextElement()).dump_with_types(out, n + 2);
    }
    dump_AbstractSymbol(out, n + 2, return_type);
    expr.dump_with_types(out, n + 2);
  }
}


/** Defines AST constructor 'attr'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
  public AbstractSymbol name;
  public AbstractSymbol type_decl;
  protected Expression init;
  /** Creates "attr" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   * @param a1 initial value for type_decl
   * @param a2 initial value for init
   */
  public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
    super(lineNumber);
    name = a1;
    type_decl = a2;
    init = a3;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    o.enterScope();
    init.checkType(f, o, m, c);
    if (name.equals(TreeConstants.self)) {
      m.semantError(f, this, "attr name can't be self");
    }
    if ((!init.get_type().equals(TreeConstants.No_type)) && !m.classIsSubclassOf(init.get_type(), type_decl, c)) {
      m.semantError(f, this, init.get_type().getString() + " is not subtype of " + type_decl + " in " + c.getName().getString() + "." + name.getString());
    }
    o.exitScope();
  }
  public TreeNode copy() {
    return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "attr\n");
    dump_AbstractSymbol(out, n+2, name);
    dump_AbstractSymbol(out, n+2, type_decl);
    init.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_attr");
    dump_AbstractSymbol(out, n + 2, name);
    dump_AbstractSymbol(out, n + 2, type_decl);
    init.dump_with_types(out, n + 2);
  }

}


/** Defines AST constructor 'formalc'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
  public AbstractSymbol name;
  public AbstractSymbol type_decl;
  /** Creates "formalc" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   * @param a1 initial value for type_decl
   */
  public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
    super(lineNumber);
    name = a1;
    type_decl = a2;
  }

  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    o.addId(name, type_decl);
  }
  public TreeNode copy() {
    return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "formalc\n");
    dump_AbstractSymbol(out, n+2, name);
    dump_AbstractSymbol(out, n+2, type_decl);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_formal");
    dump_AbstractSymbol(out, n + 2, name);
    dump_AbstractSymbol(out, n + 2, type_decl);
  }

}


/** Defines AST constructor 'branch'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
  protected AbstractSymbol name;
  protected AbstractSymbol type_decl;
  public Expression expr;
  /** Creates "branch" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   * @param a1 initial value for type_decl
   * @param a2 initial value for expr
   */
  public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
    super(lineNumber);
    name = a1;
    type_decl = a2;
    expr = a3;
  }

  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    o.enterScope();
    o.addId(name, type_decl);
    expr.checkType(f, o, m, c);
    o.exitScope();
  }
  public TreeNode copy() {
    return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "branch\n");
    dump_AbstractSymbol(out, n+2, name);
    dump_AbstractSymbol(out, n+2, type_decl);
    expr.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_branch");
    dump_AbstractSymbol(out, n + 2, name);
    dump_AbstractSymbol(out, n + 2, type_decl);
    expr.dump_with_types(out, n + 2);
  }

}


/** Defines AST constructor 'assign'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
  protected AbstractSymbol name;
  protected Expression expr;
  /** Creates "assign" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   * @param a1 initial value for expr
   */
  public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
    super(lineNumber);
    name = a1;
    expr = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    expr.checkType(f, o, m, c);
    this.set_type(expr.get_type());
  }
  public TreeNode copy() {
    return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "assign\n");
    dump_AbstractSymbol(out, n+2, name);
    expr.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_assign");
    dump_AbstractSymbol(out, n + 2, name);
    expr.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'static_dispatch'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
  protected Expression expr;
  protected AbstractSymbol type_name;
  protected AbstractSymbol name;
  protected Expressions actual;
  /** Creates "static_dispatch" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for expr
   * @param a1 initial value for type_name
   * @param a2 initial value for name
   * @param a3 initial value for actual
   */
  public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
    super(lineNumber);
    expr = a1;
    type_name = a2;
    name = a3;
    actual = a4;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    expr.checkType(f, o, m, c);

    if (!m.classIsSubclassOf(expr.get_type(), type_name, c)) {
      m.semantError(f, this, "bad static dispatch: " + expr.get_type().getString() + " is not subtype of " + type_name.getString());
    }
    ArrayList<AbstractSymbol> actualTypes = new ArrayList<AbstractSymbol>();
    for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
      Expression ex = ((Expression)e.nextElement());
      ex.checkType(f, o, m, c);
      actualTypes.add(ex.get_type());
    }
    // SELF_TYPE
    AbstractSymbol return_type = m.returnType(type_name, name, actualTypes, c);
    if (return_type == null) {
      m.semantError(f, this, "can not find method " + name.getString() + " in class " + c.getName().getString());
    }
    if (return_type.equals(TreeConstants.SELF_TYPE)) {
      return_type = expr.get_type();
    }
    this.set_type(return_type);
  }
  public TreeNode copy() {
    return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "static_dispatch\n");
    expr.dump(out, n+2);
    dump_AbstractSymbol(out, n+2, type_name);
    dump_AbstractSymbol(out, n+2, name);
    actual.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_static_dispatch");
    expr.dump_with_types(out, n + 2);
    dump_AbstractSymbol(out, n + 2, type_name);
    dump_AbstractSymbol(out, n + 2, name);
    out.println(Utilities.pad(n + 2) + "(");
    for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
      ((Expression)e.nextElement()).dump_with_types(out, n + 2);
    }
    out.println(Utilities.pad(n + 2) + ")");
    dump_type(out, n);
  }

}


/** Defines AST constructor 'dispatch'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
  protected Expression expr;
  protected AbstractSymbol name;
  protected Expressions actual;
  /** Creates "dispatch" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for expr
   * @param a1 initial value for name
   * @param a2 initial value for actual
   */
  public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
    super(lineNumber);
    expr = a1;
    name = a2;
    actual = a3;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    expr.checkType(f, o, m, c);
    ArrayList<AbstractSymbol> actualTypes = new ArrayList<AbstractSymbol>();
    for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
      Expression ex = ((Expression)e.nextElement());
      ex.checkType(f, o, m, c);
      actualTypes.add(ex.get_type());
    }
    // SELF_TYPE
    AbstractSymbol return_type = m.returnType(expr.get_type(), name, actualTypes, c);
    if (return_type == null) {
      m.semantError(f, this, "can not find method " + name.getString() + " in class " + c.getName().getString());
    }
    if (return_type.equals(TreeConstants.SELF_TYPE)) {
      return_type = expr.get_type();
    }
    this.set_type(return_type);

  }
  public TreeNode copy() {
    return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "dispatch\n");
    expr.dump(out, n+2);
    dump_AbstractSymbol(out, n+2, name);
    actual.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_dispatch");
    expr.dump_with_types(out, n + 2);
    dump_AbstractSymbol(out, n + 2, name);
    out.println(Utilities.pad(n + 2) + "(");
    for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
      ((Expression)e.nextElement()).dump_with_types(out, n + 2);
    }
    out.println(Utilities.pad(n + 2) + ")");
    dump_type(out, n);
  }

}


/** Defines AST constructor 'cond'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
  protected Expression pred;
  protected Expression then_exp;
  protected Expression else_exp;
  /** Creates "cond" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for pred
   * @param a1 initial value for then_exp
   * @param a2 initial value for else_exp
   */
  public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
    super(lineNumber);
    pred = a1;
    then_exp = a2;
    else_exp = a3;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    pred.checkType(f, o, m, c);
    if (!pred.get_type().equals(TreeConstants.Bool)) {
      m.semantError(f, this, "if condition is not bool");
    }
    then_exp.checkType(f, o, m, c);
    else_exp.checkType(f, o, m, c);
    this.set_type(m.join(then_exp.get_type(), else_exp.get_type()));
  }
  public TreeNode copy() {
    return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "cond\n");
    pred.dump(out, n+2);
    then_exp.dump(out, n+2);
    else_exp.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_cond");
    pred.dump_with_types(out, n + 2);
    then_exp.dump_with_types(out, n + 2);
    else_exp.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'loop'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
  protected Expression pred;
  protected Expression body;
  /** Creates "loop" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for pred
   * @param a1 initial value for body
   */
  public loop(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    pred = a1;
    body = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    pred.checkType(f, o, m, c);
    if (!m.classIsSubclassOf(pred.get_type(), TreeConstants.Bool, c)) {
      m.semantError(f, this, "bad loop pred: " + pred.get_type().getString() + " is not subtype of Bool");
    }
    this.set_type(TreeConstants.Object_);
  }
  public TreeNode copy() {
    return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "loop\n");
    pred.dump(out, n+2);
    body.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_loop");
    pred.dump_with_types(out, n + 2);
    body.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'typcase'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
  protected Expression expr;
  protected Cases cases;
  /** Creates "typcase" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for expr
   * @param a1 initial value for cases
   */
  public typcase(int lineNumber, Expression a1, Cases a2) {
    super(lineNumber);
    expr = a1;
    cases = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    expr.checkType(f, o, m, c);
    ArrayList<AbstractSymbol> types = new ArrayList<AbstractSymbol>();
    for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
      branch ca = ((branch)e.nextElement());
      ca.checkType(f, o, m, c);
      types.add(ca.expr.get_type());
    }
    this.set_type(m.join(types));
  }
  public TreeNode copy() {
    return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "typcase\n");
    expr.dump(out, n+2);
    cases.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_typcase");
    expr.dump_with_types(out, n + 2);
    for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
      ((Case)e.nextElement()).dump_with_types(out, n + 2);
    }
    dump_type(out, n);
  }

}


/** Defines AST constructor 'block'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
  protected Expressions body;
  /** Creates "block" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for body
   */
  public block(int lineNumber, Expressions a1) {
    super(lineNumber);
    body = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    o.enterScope();
    Expression ex = null;
    for (Enumeration e = body.getElements(); e.hasMoreElements();) {
      ex = ((Expression)e.nextElement());
      ex.checkType(f, o, m, c);
    }
    if (ex != null) {
      this.set_type(ex.get_type());
    } else {
      this.set_type(TreeConstants.No_type);
    }
    o.exitScope();
  }
  public TreeNode copy() {
    return new block(lineNumber, (Expressions)body.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "block\n");
    body.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_block");
    for (Enumeration e = body.getElements(); e.hasMoreElements();) {
      ((Expression)e.nextElement()).dump_with_types(out, n + 2);
    }
    dump_type(out, n);
  }

}


/** Defines AST constructor 'let'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
  protected AbstractSymbol identifier;
  protected AbstractSymbol type_decl;
  protected Expression init;
  protected Expression body;
  /** Creates "let" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for identifier
   * @param a1 initial value for type_decl
   * @param a2 initial value for init
   * @param a3 initial value for body
   */
  public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
    super(lineNumber);
    identifier = a1;
    type_decl = a2;
    init = a3;
    body = a4;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    o.enterScope();
    o.addId(identifier, type_decl);
    AbstractSymbol t0p = type_decl;
    if (t0p.equals(TreeConstants.SELF_TYPE)) {
      t0p = c.getName();
    }
    init.checkType(f, o, m, c);
    body.checkType(f, o, m, c);
    this.set_type(body.get_type());
    o.exitScope();
  }
  public TreeNode copy() {
    return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "let\n");
    dump_AbstractSymbol(out, n+2, identifier);
    dump_AbstractSymbol(out, n+2, type_decl);
    init.dump(out, n+2);
    body.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_let");
    dump_AbstractSymbol(out, n + 2, identifier);
    dump_AbstractSymbol(out, n + 2, type_decl);
    init.dump_with_types(out, n + 2);
    body.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'plus'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "plus" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public plus(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);

    assertType(e1.get_type(), TreeConstants.Int, f, m, c);
    assertType(e2.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Int);
  }
  public TreeNode copy() {
    return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "plus\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_plus");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'sub'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "sub" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public sub(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);

    assertType(e1.get_type(), TreeConstants.Int, f, m, c);
    assertType(e2.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Int);
  }
  public TreeNode copy() {
    return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "sub\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_sub");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'mul'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "mul" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public mul(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);

    assertType(e1.get_type(), TreeConstants.Int, f, m, c);
    assertType(e2.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Int);
  }
  public TreeNode copy() {
    return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "mul\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_mul");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'divide'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "divide" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public divide(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);

    assertType(e1.get_type(), TreeConstants.Int, f, m, c);
    assertType(e2.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Int);
  }

  public TreeNode copy() {
    return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "divide\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_divide");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'neg'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
  protected Expression e1;
  /** Creates "neg" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   */
  public neg(int lineNumber, Expression a1) {
    super(lineNumber);
    e1 = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);

    assertType(e1.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Int);
  }
  public TreeNode copy() {
    return new neg(lineNumber, (Expression)e1.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "neg\n");
    e1.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_neg");
    e1.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'lt'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "lt" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public lt(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);

    assertType(e1.get_type(), TreeConstants.Int, f, m, c);
    assertType(e2.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Bool);
  }
  public TreeNode copy() {
    return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "lt\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_lt");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'eq'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "eq" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public eq(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);

 
    this.set_type(TreeConstants.Bool);
    // TODO checkType
  }
  public TreeNode copy() {
    return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "eq\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_eq");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'leq'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
  protected Expression e1;
  protected Expression e2;
  /** Creates "leq" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   * @param a1 initial value for e2
   */
  public leq(int lineNumber, Expression a1, Expression a2) {
    super(lineNumber);
    e1 = a1;
    e2 = a2;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);
    e2.checkType(f, o, m, c);
 
    assertType(e1.get_type(), TreeConstants.Int, f, m, c);
    assertType(e2.get_type(), TreeConstants.Int, f, m, c);

    this.set_type(TreeConstants.Bool);
  }
  public TreeNode copy() {
    return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "leq\n");
    e1.dump(out, n+2);
    e2.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_leq");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'comp'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
  protected Expression e1;
  /** Creates "comp" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   */
  public comp(int lineNumber, Expression a1) {
    super(lineNumber);
    e1 = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }
    e1.checkType(f, o, m, c);

    // TODO checkType
    // Why does comp only have an e1?
 
    this.set_type(TreeConstants.Bool);
  }
  public TreeNode copy() {
    return new comp(lineNumber, (Expression)e1.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "comp\n");
    e1.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_comp");
    e1.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'int_const'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
  protected AbstractSymbol token;
  /** Creates "int_const" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for token
   */
  public int_const(int lineNumber, AbstractSymbol a1) {
    super(lineNumber);
    token = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    this.set_type(TreeConstants.Int);
  }

  public TreeNode copy() {
    return new int_const(lineNumber, copy_AbstractSymbol(token));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "int_const\n");
    dump_AbstractSymbol(out, n+2, token);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_int");
    dump_AbstractSymbol(out, n + 2, token);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'bool_const'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
  protected Boolean val;
  /** Creates "bool_const" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for val
   */
  public bool_const(int lineNumber, Boolean a1) {
    super(lineNumber);
    val = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    this.set_type(TreeConstants.Bool);
  }

  public TreeNode copy() {
    return new bool_const(lineNumber, copy_Boolean(val));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "bool_const\n");
    dump_Boolean(out, n+2, val);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_bool");
    dump_Boolean(out, n + 2, val);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'string_const'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
  protected AbstractSymbol token;
  /** Creates "string_const" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for token
   */
  public string_const(int lineNumber, AbstractSymbol a1) {
    super(lineNumber);
    token = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    this.set_type(TreeConstants.Str);
  }
  public TreeNode copy() {
    return new string_const(lineNumber, copy_AbstractSymbol(token));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "string_const\n");
    dump_AbstractSymbol(out, n+2, token);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_string");
    out.print(Utilities.pad(n + 2) + "\"");
    Utilities.printEscapedString(out, token.getString());
    out.println("\"");
    dump_type(out, n);
  }

}


/** Defines AST constructor 'new_'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
  protected AbstractSymbol type_name;
  /** Creates "new_" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for type_name
   */
  public new_(int lineNumber, AbstractSymbol a1) {
    super(lineNumber);
    type_name = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    this.set_type(type_name);
  }
  public TreeNode copy() {
    return new new_(lineNumber, copy_AbstractSymbol(type_name));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "new_\n");
    dump_AbstractSymbol(out, n+2, type_name);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_new");
    dump_AbstractSymbol(out, n + 2, type_name);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'isvoid'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
  protected Expression e1;
  /** Creates "isvoid" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for e1
   */
  public isvoid(int lineNumber, Expression a1) {
    super(lineNumber);
    e1 = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    e1.checkType(f, o, m, c);
    this.set_type(TreeConstants.Bool);
  }
  public TreeNode copy() {
    return new isvoid(lineNumber, (Expression)e1.copy());
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "isvoid\n");
    e1.dump(out, n+2);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_isvoid");
    e1.dump_with_types(out, n + 2);
    dump_type(out, n);
  }

}


/** Defines AST constructor 'no_expr'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
  /** Creates "no_expr" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   */
  public no_expr(int lineNumber) {
    super(lineNumber);
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    this.set_type(TreeConstants.No_type);
  }
  public TreeNode copy() {
    return new no_expr(lineNumber);
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "no_expr\n");
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_no_expr");
    dump_type(out, n);
  }

}


/** Defines AST constructor 'object'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
  protected AbstractSymbol name;
  /** Creates "object" AST node. 
   *
   * @param lineNumber the line in the source file from which this node came.
   * @param a0 initial value for name
   */
  public object(int lineNumber, AbstractSymbol a1) {
    super(lineNumber);
    name = a1;
  }
  public void checkType(AbstractSymbol f, SymbolTable o, ClassTable m, class_c c) {
    if (Flags.semant_debug) {
      System.err.print("checkType ");
      dump_line(System.err, 0);
    }

    AbstractSymbol s = (AbstractSymbol)o.lookup(name);
    if (s == null) {
      s = m.attrType(c.getName(), name);
      if (s == null) {
        m.semantError(f, this, "undefined symbol " + name.getString());
        s = TreeConstants.Object_;
      }
    }
    this.set_type(s);
  }
  public TreeNode copy() {
    return new object(lineNumber, copy_AbstractSymbol(name));
  }
  public void dump(PrintStream out, int n) {
    out.print(Utilities.pad(n) + "object\n");
    dump_AbstractSymbol(out, n+2, name);
  }


  public void dump_with_types(PrintStream out, int n) {
    dump_line(out, n);
    out.println(Utilities.pad(n) + "_object");
    dump_AbstractSymbol(out, n + 2, name);
    dump_type(out, n);
  }

}


