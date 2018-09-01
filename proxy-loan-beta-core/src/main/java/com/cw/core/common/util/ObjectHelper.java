package com.cw.core.common.util;


import java.util.Collection;
import java.util.Map;

public class ObjectHelper {
    public static boolean isEquals(Object object1, Object object2) {
        boolean ret = false;// 47

        try {
            if (object1 == null && object2 == null) {// 49
                ret = true;// 50
                return ret;// 51
            }

            ret = object1.equals(object2);// 53
        } catch (NullPointerException arg3) {// 54
            ret = false;// 55
        }

        return ret;// 57
    }

    public static boolean equalsIgnorecase(String s1, String s2) {
        return s1 == null && s2 == null ? true : s1 != null && s2 != null && s1.toLowerCase().equals(s2.toLowerCase());// 69 70 71 72
    }

//	public static void main(String[] at){
//		ArrayList<String> data=new ArrayList<>();
//		System.out.print(isNotEmpty(data));
//	}

    public static boolean isEmpty(Object obj) {
        if (obj == null) {// 88
            return true;// 89
        } else if (obj instanceof Map) {// 91
            return ((Map) obj).entrySet().isEmpty();// 92
        } else if (obj instanceof Collection) {// 95
            return obj != null ? ((Collection) obj).isEmpty() : true;// 96
        } else if (obj instanceof String) {// 99
            return ((String) obj).equalsIgnoreCase("null") | ((String) obj).trim().isEmpty();// 100
        } else if (obj instanceof StringBuffer) {// 105
            return ((StringBuffer) obj).length() == 0;// 106
        } else {
            if (obj.getClass().isArray()) {// 109
                try {
                    Object[] a = (Object[]) ((Object[]) obj);// 111
                    boolean b = true;// 113
                    Object[] arg2 = a;
                    int arg3 = a.length;

                    for (int arg4 = 0; arg4 < arg3; ++arg4) {// 114
                        Object o = arg2[arg4];
                        b &= isEmpty(o);// 115
                        if (!b) {// 117
                            break;
                        }
                    }

                    return b;// 121
                } catch (ClassCastException arg6) {// 122
                    ;
                }
            }

            return false;// 126
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);// 138
    }

}