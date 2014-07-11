package nc.liat6.frame.lib.org.objectweb.asm;

public interface FieldVisitor{

  AnnotationVisitor visitAnnotation(String desc,boolean visible);

  void visitAttribute(Attribute attr);

  void visitEnd();
}
