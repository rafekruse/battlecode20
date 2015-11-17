package battlecode.doc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.standard.Standard;

import java.util.Arrays;
import java.util.Comparator;

public class Doclet extends Standard {

    public static boolean start(RootDoc root) {
        // Due to various issues with the javadoc api we have to
        // use some bad hacks to tell the bytecode cost taglet the
        // method names.
        StringBuilder methodBuilder = new StringBuilder();
        StringBuilder memberBuilder = new StringBuilder();
        ClassDoc[] doc = root.classes();
        Arrays.sort(doc, (o1, o2) -> o1.qualifiedName().compareToIgnoreCase(o2.qualifiedName()));
        for (ClassDoc cl : doc) {
            String clname = cl.qualifiedName().replace(".", "/");
            for (MethodDoc m : cl.methods()) {
                methodBuilder.append(clname);
                methodBuilder.append("/");
                methodBuilder.append(m.name());
                methodBuilder.append("\n");
            }
            for (FieldDoc f : cl.enumConstants()) {
                memberBuilder.append(f.containingClass().name());
                memberBuilder.append('.');
                memberBuilder.append(f.name());
                memberBuilder.append("\n");
            }
            for (FieldDoc f : cl.fields()) {
                memberBuilder.append(f.containingClass().name());
                memberBuilder.append('.');
                memberBuilder.append(f.name());
                memberBuilder.append("\n");
            }
        }
        System.setProperty("battlecode.doc.methods", methodBuilder.toString());
        System.setProperty("battlecode.doc.members", memberBuilder.toString());
        return Standard.start(root);
    }

}
