import java.io.PrintStream;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Enumeration;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
  private int semantErrors;
  private PrintStream errorStream;

  private Hashtable<String, AbstractSymbol> inheritanceTable = new Hashtable<String, AbstractSymbol>();
  private Hashtable<String, class_c> classNameTable = new Hashtable<String, class_c>();

  private class_c Object_class;
  private class_c IO_class;
  private class_c Int_class;
  private class_c Bool_class;
  private class_c Str_class;

  /** Creates data structures representing basic Cool classes (Object,
   * IO, Int, Bool, String).  Please note: as is this method does not
   * do anything useful; you will need to edit it to make if do what
   * you want.
   * */
  private void installBasicClasses() {
    AbstractSymbol filename 
      = AbstractTable.stringtable.addString("<basic class>");

    // The following demonstrates how to create dummy parse trees to
    // refer to basic Cool classes.  There's no need for method
    // bodies -- these are already built into the runtime system.

    // IMPORTANT: The results of the following expressions are
    // stored in local variables.  You will want to do something
    // with those variables at the end of this method to make this
    // code meaningful.

    // The Object class has no parent class. Its methods are
    //        cool_abort() : Object    aborts the program
    //        type_name() : Str        returns a string representation 
    //                                 of class name
    //        copy() : SELF_TYPE       returns a copy of the object

    Object_class = 
      new class_c(0, 
          TreeConstants.Object_, 
          TreeConstants.No_class,
          new Features(0)
          .appendElement(new method(0, 
              TreeConstants.cool_abort, 
              new Formals(0), 
              TreeConstants.Object_, 
              new no_expr(0)))
          .appendElement(new method(0,
              TreeConstants.type_name,
              new Formals(0),
              TreeConstants.Str,
              new no_expr(0)))
          .appendElement(new method(0,
              TreeConstants.copy,
              new Formals(0),
              TreeConstants.SELF_TYPE,
              new no_expr(0))),
          filename);

    // The IO class inherits from Object. Its methods are
    //        out_string(Str) : SELF_TYPE  writes a string to the output
    //        out_int(Int) : SELF_TYPE      "    an int    "  "     "
    //        in_string() : Str            reads a string from the input
    //        in_int() : Int                "   an int     "  "     "

    IO_class = 
      new class_c(0,
          TreeConstants.IO,
          TreeConstants.Object_,
          new Features(0)
          .appendElement(new method(0,
              TreeConstants.out_string,
              new Formals(0)
              .appendElement(new formalc(0,
                  TreeConstants.arg,
                  TreeConstants.Str)),
              TreeConstants.SELF_TYPE,
              new no_expr(0)))
          .appendElement(new method(0,
              TreeConstants.out_int,
              new Formals(0)
              .appendElement(new formalc(0,
                  TreeConstants.arg,
                  TreeConstants.Int)),
              TreeConstants.SELF_TYPE,
              new no_expr(0)))
          .appendElement(new method(0,
              TreeConstants.in_string,
              new Formals(0),
              TreeConstants.Str,
              new no_expr(0)))
          .appendElement(new method(0,
                TreeConstants.in_int,
                new Formals(0),
                TreeConstants.Int,
                new no_expr(0))),
      filename);

    // The Int class has no methods and only a single attribute, the
    // "val" for the integer.

    Int_class = 
      new class_c(0,
          TreeConstants.Int,
          TreeConstants.Object_,
          new Features(0)
          .appendElement(new attr(0,
              TreeConstants.val,
              TreeConstants.prim_slot,
              new no_expr(0))),
          filename);

    // Bool also has only the "val" slot.
    Bool_class = 
      new class_c(0,
          TreeConstants.Bool,
          TreeConstants.Object_,
          new Features(0)
          .appendElement(new attr(0,
              TreeConstants.val,
              TreeConstants.prim_slot,
              new no_expr(0))),
          filename);

    // The class Str has a number of slots and operations:
    //       val                              the length of the string
    //       str_field                        the string itself
    //       length() : Int                   returns length of the string
    //       concat(arg: Str) : Str           performs string concatenation
    //       substr(arg: Int, arg2: Int): Str substring selection

    Str_class =
      new class_c(0,
          TreeConstants.Str,
          TreeConstants.Object_,
          new Features(0)
          .appendElement(new attr(0,
              TreeConstants.val,
              TreeConstants.Int,
              new no_expr(0)))
          .appendElement(new attr(0,
              TreeConstants.str_field,
              TreeConstants.prim_slot,
              new no_expr(0)))
          .appendElement(new method(0,
              TreeConstants.length,
              new Formals(0),
              TreeConstants.Int,
              new no_expr(0)))
          .appendElement(new method(0,
              TreeConstants.concat,
              new Formals(0)
              .appendElement(new formalc(0,
                  TreeConstants.arg, 
                  TreeConstants.Str)),
              TreeConstants.Str,
              new no_expr(0)))
          .appendElement(new method(0,
                TreeConstants.substr,
                new Formals(0)
                .appendElement(new formalc(0,
                    TreeConstants.arg,
                    TreeConstants.Int))
                .appendElement(new formalc(0,
                    TreeConstants.arg2,
                    TreeConstants.Int)),
                TreeConstants.Str,
                new no_expr(0))),
      filename);

    /* Do somethind with Object_class, IO_class, Int_class,
       Bool_class, and Str_class here */
    classNameTable.put(Object_class.getName().getString(), Object_class);
    inheritanceTable.put(IO_class.getName().getString(), Object_class.getName());
    classNameTable.put(IO_class.getName().getString(), IO_class);
    inheritanceTable.put(Int_class.getName().getString(), Object_class.getName());
    classNameTable.put(Int_class.getName().getString(), Int_class);
    inheritanceTable.put(Bool_class.getName().getString(), Object_class.getName());
    classNameTable.put(Bool_class.getName().getString(), Int_class);
    inheritanceTable.put(Str_class.getName().getString(), Object_class.getName());
    classNameTable.put(Str_class.getName().getString(), Str_class);

  }



  public ClassTable(Classes cls) {
    semantErrors = 0;
    errorStream = System.err;

    installBasicClasses();
    
    // add inheritance info
    HashSet<String> allClasses = new HashSet<String>();

    allClasses.add(IO_class.getName().getString());
    allClasses.add(Int_class.getName().getString());
    allClasses.add(Str_class.getName().getString());
    allClasses.add(Object_class.getName().getString());

    for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
      class_c c = (class_c)e.nextElement();
      AbstractSymbol parent = c.getParent();
      if (parent.equals(Bool_class.getName()) ||
         parent.equals(Str_class.getName()) ||
         parent.equals(Int_class.getName())) {
        semantError(c).println("class " + c.getName().getString() + " can't inherit " + c.getParent().getString());
      }
      if (c.getName().equals(TreeConstants.SELF_TYPE)) {
        semantError(c).println("class " + c.getName().getString() + " can't use name SELF_TYPE");
      }
      inheritanceTable.put(c.getName().getString(), parent);
      classNameTable.put(c.getName().getString(), c);
      boolean existed = !allClasses.add(c.getName().getString());
      if (existed) {
        semantError(c).println("class redefined " + c.getName().getString());
        return;
      }
    }

    // check parent existed
    for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
      class_c c = (class_c)e.nextElement();
      boolean existed = !allClasses.add(c.getParent().getString());
      if (!existed) {
        semantError(c).println("class not found " + c.getParent().getString());
      }
    }

    // check Main exists
    if (!allClasses.contains(TreeConstants.Main.getString())) {
        semantError().println("Class Main is not defined.");
    }

    // check every class
    HashSet<String> s;
    for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
      class_c cc = (class_c)e.nextElement();
      AbstractSymbol c = cc.getName();
      // check inheritance
      s = new HashSet<String>();
      while (true) {
        AbstractSymbol p = inheritanceTable.get(c.getString());
        if (p == null) {
          break;
        } 
        boolean existed = !s.add(p.getString());
        if (existed) {
          semantError(cc).println("inheritance is not a tree");
          return;
        }
        c = p;
      }
    }
  }

  /** Prints line number and file name of the given class.
   *
   * Also increments semantic error count.
   *
   * @param c the class
   * @return a print stream to which the rest of the error message is
   * to be printed.
   *
   * */
  public PrintStream semantError(class_c c) {
    return semantError(c.getFilename(), c);
  }

  /** Prints the file name and the line number of the given tree node.
   *
   * Also increments semantic error count.
   *
   * @param filename the file name
   * @param t the tree node
   * @return a print stream to which the rest of the error message is
   * to be printed.
   *
   * */
  public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
    errorStream.print(filename + ":" + t.getLineNumber() + ": ");
    return semantError();
  }
  
  public PrintStream semantError(AbstractSymbol filename, TreeNode t, String msg) {
    PrintStream s = semantError(filename, t);
    s.println(msg);
    return s;
  }

  /** Increments semantic error count and returns the print stream for
   * error messages.
   *
   * @return a print stream to which the error message is
   * to be printed.
   *
   * */
  public PrintStream semantError() {
    semantErrors++;
    return errorStream;
  }

  /** Returns true if there are any static semantic errors. */
  public boolean errors() {
    return semantErrors != 0;
  }

  public AbstractSymbol join(AbstractSymbol first, AbstractSymbol second, class_c currentInClass) {
    // if both are SELF_TYPE, return SELF_TYPE
    if (first.equals(second)) {
      return first;
    }
    if (first.equals(TreeConstants.SELF_TYPE)) {
      first = currentInClass.getName();
    }
    if (second.equals(TreeConstants.SELF_TYPE)) {
      second = currentInClass.getName();
    }
    ArrayList<AbstractSymbol> fp = new ArrayList<AbstractSymbol>();
    ArrayList<AbstractSymbol> sp = new ArrayList<AbstractSymbol>();
    AbstractSymbol c = first;
    while (true) {
      fp.add(c);
      AbstractSymbol p = inheritanceTable.get(c.getString());
      if (p == null) {
        break;
      }
      c = p;
    }
    c = second;
    while (true) {
      sp.add(c);
      AbstractSymbol p = inheritanceTable.get(c.getString());
      if (p == null) {
        break;
      }
      c = p;
    }
    int fi = fp.size() - 1;
    int si = sp.size() - 1;
    AbstractSymbol result = null;
    while (fi >= 0 && si >= 0) {
      AbstractSymbol s = sp.get(si);
      if (fp.get(fi).equals(s)) {
        result = s;
      } else {
        break;
      }
      fi--;
      si--;
    }
    return result;
  }

  public AbstractSymbol join(ArrayList<AbstractSymbol> classes, class_c currentInClass) {
    // TODO: this is very slow, replace this algorithm with a fast one
    AbstractSymbol result = classes.get(0);
    for (int i = 1; i < classes.size(); i++) {
      result = join(result, classes.get(i), currentInClass);
    }
    return result;
  }

  public class_c lookupParent(AbstractSymbol name) {
    return classNameTable.get(inheritanceTable.get(name.getString()).getString());
  }

  public class_c lookupClass(AbstractSymbol name) {
    return classNameTable.get(name.getString());
  }

  public boolean classIsSubclassOf(AbstractSymbol child, AbstractSymbol parent, class_c currentInClass) {
    if (child == null || parent == null) {
      return false;
    }
    // two SELF_TYPE are true
    if (child.equals(parent)) {
      return true;
    }
    if (child.equals(TreeConstants.SELF_TYPE)) {
      child = currentInClass.getName();
    }
    // yes, check again
    if (child.equals(parent)) {
      return true;
    }
    AbstractSymbol c = child;
    while (true) {
      AbstractSymbol p = inheritanceTable.get(c.getString());
      if (p == null) {
        return false;
      } else if (p.equals(parent)) {
        return true;
      }
      c = p;
    }
  }

  public AbstractSymbol attrType(AbstractSymbol class_, AbstractSymbol attrName) {
    AbstractSymbol c = class_;
    while (true) {
      class_c c_c = classNameTable.get(c.getString());
      if (c_c == null) {
        semantError().println("can not find class " + c.getString());
        return null;
      }
      Features features = c_c.features;
      for (Enumeration e = features.getElements(); e.hasMoreElements();) {
        Feature f = (Feature)e.nextElement();
        if (f instanceof attr) {
          attr a = (attr)f;
          if (a.name.equals(attrName)) {
            return a.type_decl;
          }
        }
      }
      AbstractSymbol p = inheritanceTable.get(c.getString());
      if (p == null) {
        break;
      }
      c = p;
    }
    return null;
  }

  public AbstractSymbol returnType(AbstractSymbol class_, AbstractSymbol methodName, ArrayList<AbstractSymbol> actualTypes, class_c currentInClass) {
    if (class_.equals(TreeConstants.SELF_TYPE)) {
      class_ = currentInClass.getName();
    }
    AbstractSymbol c = class_;
    while (true) {
      class_c c_c = classNameTable.get(c.getString());
      if (c_c == null) {
        semantError().println("can not find class " + c.getString());
        return null;
      }
      Features features = c_c.features;
      for (Enumeration e = features.getElements(); e.hasMoreElements();) {
        Feature f = (Feature)e.nextElement();
        if (f instanceof method) {
          method m = (method)f;
          if (m.name.equals(methodName)) {
            int i = 0;
            boolean wrong = false;
            for (Enumeration e1 = m.formals.getElements(); e1.hasMoreElements();) {
              formalc fe = ((formalc)e1.nextElement());
              if (!this.classIsSubclassOf(actualTypes.get(i), fe.type_decl, currentInClass)) {
                wrong = true;
                break;
              }
              i++;
            }
            if (wrong) {
              continue;
            } else {
              return m.return_type;
            }
          }
        }

      }
      AbstractSymbol p = inheritanceTable.get(c.getString());
      if (p == null) {
        break;
      }
      c = p;
    }
    return null;
  }
}


