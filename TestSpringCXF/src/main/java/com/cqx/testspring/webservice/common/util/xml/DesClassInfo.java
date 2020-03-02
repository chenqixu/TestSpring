package com.cqx.testspring.webservice.common.util.xml;

import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * DesClassInfo
 *
 * @author chenqixu
 */
public class DesClassInfo {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final Map<Class, DesClassInfo> CLASS_INFO_MAP = Collections.synchronizedMap(new HashMap());
    private String className;
    private String[] readablePropertyNames;
    private String[] writeablePropertyNames;
    private HashMap<String, Method> setMethods;
    private HashMap<String, Method> getMethods;
    private HashMap<String, Class> setTypes;
    private HashMap<String, Class> getTypes;
    private HashMap<String, Field> setFields;
    private HashMap fieldsNameMap;

    private DesClassInfo(Class clazz) {
        this.readablePropertyNames = EMPTY_STRING_ARRAY;
        this.writeablePropertyNames = EMPTY_STRING_ARRAY;
        this.setMethods = new HashMap();
        this.getMethods = new HashMap();
        this.setTypes = new HashMap();
        this.getTypes = new HashMap();
        this.setFields = new HashMap();
        this.fieldsNameMap = new HashMap();
        this.className = clazz.getName();

        try {
            Field[] aField = clazz.getFields();

            int i;
            for (i = 0; i < aField.length; ++i) {
                this.setFields.put(fieldDropCase(aField[i].getName()), aField[i]);
                this.fieldsNameMap.put(fieldDropCase(aField[i].getName()), aField[i].getName());
            }

            aField = clazz.getDeclaredFields();

            for (i = 0; i < aField.length; ++i) {
                this.setFields.put(fieldDropCase(aField[i].getName()), aField[i]);
                this.fieldsNameMap.put(fieldDropCase(aField[i].getName()), aField[i].getName());
            }
        } catch (SecurityException var4) {
            System.out.println(var4.getMessage());
        }

        this.addMethods(clazz);

        for (Class superClass = clazz.getSuperclass(); superClass != null; superClass = superClass.getSuperclass()) {
            this.addMethods(superClass);
        }

        this.readablePropertyNames = (String[]) this.fieldsNameMap.values().toArray(new String[this.fieldsNameMap.values().size()]);
        this.writeablePropertyNames = (String[]) this.fieldsNameMap.values().toArray(new String[this.fieldsNameMap.values().size()]);
    }

    private void addMethods(Class cls) {
        Method[] methods = cls.getMethods();

        for (int i = 0; i < methods.length; ++i) {
            String name = methods[i].getName();
            if (!"class".equals(name)) {
                if (name.startsWith("set") && name.length() > 3) {
                    if (methods[i].getParameterTypes().length == 1) {
                        name = dropCase(name);
                        this.setMethods.put(this.getFileName(name), methods[i]);
                        this.setTypes.put(this.getFileName(name), methods[i].getParameterTypes()[0]);
                    }
                } else if (name.startsWith("get") && name.length() > 3) {
                    if (methods[i].getParameterTypes().length == 0) {
                        name = dropCase(name);
                        this.getMethods.put(this.getFileName(name), methods[i]);
                        this.getTypes.put(this.getFileName(name), methods[i].getReturnType());
                    }
                } else if (name.startsWith("is") && name.length() > 2 && methods[i].getParameterTypes().length == 0) {
                    name = dropCase(name);
                    this.getMethods.put(this.getFileName(name), methods[i]);
                    this.getTypes.put(this.getFileName(name), methods[i].getReturnType());
                }

                name = null;
            }
        }

    }

    private static String dropCase(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (!name.startsWith("get") && !name.startsWith("set")) {
            System.out.println("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        } else {
            name = name.substring(3);
        }

        return name.toLowerCase(Locale.US);
    }

    private static String fieldDropCase(String name) {
        return name.toLowerCase(Locale.US);
    }

    private String getFileName(String name) {
        try {
            name = ((Field) this.setFields.get(name.toLowerCase(Locale.US))).getName();
        } catch (Exception var3) {
        }

        return name;
    }

    public String getClassName() {
        return this.className;
    }

    public Method getSetter(String propertyName) {
        Method method = (Method) this.setMethods.get(propertyName);
        if (method == null) {
            System.out.println("There is no WRITEABLE property named '" + propertyName + "' in class '" + this.className + "'");
        }

        return method;
    }

    public Method getGetter(String propertyName) {
        Method method = (Method) this.getMethods.get(propertyName);
        if (method == null) {
            System.out.println("There is no READABLE property named '" + propertyName + "' in class '" + this.className + "'");
        }

        return method;
    }

    public Class getSetterType(String propertyName) {
        Class clazz = (Class) this.setTypes.get(propertyName);
        if (clazz == null) {
            System.out.println("There is no WRITEABLE property named '" + propertyName + "' in class '" + this.className + "'");
        }

        return clazz;
    }

    public Class getGetterType(String propertyName) {
        Class clazz = (Class) this.getTypes.get(propertyName);
        if (clazz == null) {
            System.out.println("There is no READABLE property named '" + propertyName + "' in class '" + this.className + "'");
        }

        return clazz;
    }

    public String[] getReadablePropertyNames() {
        return this.readablePropertyNames;
    }

    public String[] getWriteablePropertyNames() {
        return this.writeablePropertyNames;
    }

    public boolean hasWritableProperty(String propertyName) {
        return this.setMethods.keySet().contains(propertyName);
    }

    public boolean hasReadableProperty(String propertyName) {
        return this.getMethods.keySet().contains(propertyName);
    }

    public static DesClassInfo getInstance(Class clazz) {
        synchronized (clazz) {
            DesClassInfo cache = (DesClassInfo) CLASS_INFO_MAP.get(clazz);
            if (cache == null) {
                cache = new DesClassInfo(clazz);
                CLASS_INFO_MAP.put(clazz, cache);
            }

            return cache;
        }
    }

    public Field getField(String fieldName) {
        Field field = (Field) this.setFields.get(fieldName.toLowerCase(Locale.US));
        return field;
    }

    public Class getWriteableFieldType(String fieldName) {
        Field field = (Field) this.setFields.get(fieldName.toLowerCase(Locale.US));
        return field == null ? null : field.getType();
    }

    public List<Class> getFieldGenericTypes(String fieldName) {
        List<Class> list = new ArrayList();
        Field field = (Field) this.setFields.get(fieldName.toLowerCase(Locale.US));
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Type[] var9 = types;
            int var8 = types.length;

            for (int var7 = 0; var7 < var8; ++var7) {
                Type t = var9[var7];
                if (t.getClass().getName().equals(GenericArrayTypeImpl.class.getName())) {
                    list.add(StringArray.class);
                } else {
                    list.add((Class) t);
                }
            }
        }

        return list;
    }

    public List<Class> getGenericReturnTypes(Method method) {
        List<Class> list = new ArrayList();
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) returnType).getActualTypeArguments();
            Type[] var8 = types;
            int var7 = types.length;

            for (int var6 = 0; var6 < var7; ++var6) {
                Type type = var8[var6];
                list.add((Class) type);
            }
        }

        return list;
    }

    public List<Class> getGenericParameterTypes(Method method) {
        List<Class> list = new ArrayList();
        Type[] paramTypeList = method.getGenericParameterTypes();
        Type[] var7 = paramTypeList;
        int var6 = paramTypeList.length;

        for (int var5 = 0; var5 < var6; ++var5) {
            Type paramType = var7[var5];
            if (paramType instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) paramType).getActualTypeArguments();
                Type[] var12 = types;
                int var11 = types.length;

                for (int var10 = 0; var10 < var11; ++var10) {
                    Type type = var12[var10];
                    list.add((Class) type);
                }
            }
        }

        return list;
    }
}
