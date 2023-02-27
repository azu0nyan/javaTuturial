package ru.tutorial.other;

public class V2 {

    static final V2 zeroVector = new V2(0, 0);
    static final V2 xVector = new V2(1, 0);
    static final V2 yVector = new V2(0, 1);
    public static V2 infVector = new V2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);


    public final double x, y;

    public V2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Суммирует вектор с аргументом покоординатно
     *
     * @param v слагаемое
     * @return сумма вектора и аргумента
     */
    public V2 add(V2 v) {
        return new V2(x + v.x, y + v.y);
    }

    /**
     * @param v1 слагаемое 1
     * @param v2 слагаемое 2
     * @return покоординатная сумма v1  + v2
     */
    public static V2 add(V2 v1, V2 v2) {
        return v1.add(v2);
    }

    /**
     * Умножает вектора покоординатно
     *
     * @param v множитель
     * @return произведение вектора и аргумента
     */
    public V2 mull(V2 v) {
        return new V2(x * v.x, y * v.y);
    }

    /**
     * @param v1 множитель 1
     * @param v2 множитель 2
     * @return покоординатное произведение v1 * v2
     */
    public static V2 mull(V2 v1, V2 v2) {
        return v1.mull(v2);
    }

    /**
     * Вычитает аргумент из вектора покоординатно
     *
     * @param v вычитаемое
     * @return разность вектора и аргумента
     */
    public V2 sub(V2 v) {
        return new V2(x - v.x, y - v.y);
    }

    /**
     * @param v1 уменьшаемое
     * @param v2 ввычитаемое
     * @return покоординатная разность v1 - v2
     */
    public static V2 sub(V2 v1, V2 v2) {
        return v1.sub(v2);
    }

    /**
     * Делит вектор на аргумент покоординатно
     *
     * @param v делитель
     * @return частное вектора и аргумента
     */
    public V2 div(V2 v) {
        return new V2(v.x == 0 ? 0 : x / v.x, v.y == 0 ? 0 : y / v.y);
    }

    /**
     * @param v1 делимое
     * @param v2 делитель
     * @return покоординатное частное v1 / v2
     */
    public static V2 div(V2 v1, V2 v2) {
        return v1.div(v2);
    }

    /**
     * @return строковое предствавление вектора
     */
    @Override
    public String toString() {
        return "{" + x + ", " + y + '}';
    }

    /**
     * Масштабирует вектор
     *
     * @param d масштаб
     * @return масштабированный вектор
     */
    public V2 scale(double d) {
        return new V2(x * d, y * d);
    }

    /**
     * Масштабирует вектор
     *
     * @param v вектор
     * @param d масштаб
     * @return масштабированный вектор
     */
    public static V2 scale(V2 v, double d) {
        return v.scale(d);
    }

    /**
     * @return вектор направленный в противоположную сторону(развернутый на 180 градусов), такой же длинны
     */
    public V2 opposite() {
        return scale(-1);
    }

    /**
     * @param v ветор
     * @return вектор направленный в противоположную сторону(развернутый на 180 градусов), такой же длинны
     */
    public static V2 opposite(V2 v) {
        return v.scale(-1);
    }

    /**
     * поворачивает вектор на угол, против часовой стрелки(в обычной системе координат)
     *
     * @param a угол поворота
     * @return вектор повернутый на угол а
     */
    public V2 rotate(double a) {
        return new V2((double) (x * Math.cos(a) + y * Math.sin(a)), (double) (x * -Math.sin(a) + y * Math.cos(a)));
    }

    /**
     * поворачивает вектор на угол, против часовой стрелки(в обычной системе координат)
     *
     * @param a угол поворота
     * @param v вектор
     * @return вектор повернутый на угол а
     */
    public static V2 rotate(V2 v, double a) {
        return v.rotate(a);
    }

    public V2 rotate90() {
        return new V2(-y, x);
    }

    /**
     * @return длинна вектора
     */
    public double length() {
        return (double) Math.sqrt(x * x + y * y);
    }

    /**
     * @param v вектор
     * @return длинна вектора
     */
    public static double length(V2 v) {
        return v.length();
    }

    /**
     * Нормализует вектор
     *
     * @return вектор длинны 1 смотрящий в том же направлении что и данный
     */
    public V2 normalize() {
        if (length() == 0) {
            return new V2(0, 0);
        }
        return scale(1 / length());
    }

    /**
     * Нормализует вектор
     *
     * @param v вектор
     * @return вектор длинны 1 смотрящий в том же направлении что и данный
     */
    public static V2 normalize(V2 v) {
        return v.normalize();
    }

    /**
     * скалярное произведение
     *
     * @param v множитель
     * @return скалярное произведенние вектора с аргументом функции
     */
    public double scalarProduct(V2 v) {
        return x * v.x + y * v.y;
    }

    /**
     * Скалярное произведение
     *
     * @param v1 множитель 1
     * @param v2 множитель 2
     * @return скалярное произведение v1 и v2
     */
    public static double scalarProduct(V2 v1, V2 v2) {
        return v1.scalarProduct(v2);
    }

    /**
     * @param v
     * @return ориентированный угол между вектором и аргументом функции от -Пи до Пи
     */
    public double angle(V2 v) {
        return (double) (Math.atan2(v.y, v.x) - Math.atan2(y, x));
    }

    /**
     * @param v1 вектор 1
     * @param v2 вектор 2
     * @return ориентированный угол между v1 и v2
     */
    public static double angle(V2 v1, V2 v2) {
        return v1.angle(v2);
    }

    /**
     * @return координата x вектора
     */
    public double getX() {
        return x;
    }

    /**
     * @return координата y вектора
     */
    public double getY() {
        return y;
    }

    /**
     * @return координата x вектора, небезопасно т.к. double.MAX_VALUE > Integer.MAX_VALUE
     */
    public int getXInt() {
        return (int) x;
    }

    /**
     * @return координата y вектора небезопасно т.к. double.MAX_VALUE > Integer.MAX_VALUE
     */
    public int getYInt() {
        return (int) y;
    }

    public double distance(V2 v) {
        return distance(this, v);
    }

    public static double distance(V2 v1, V2 v2) {
        return v1.sub(v2).length();
    }



    public double[] toArray() {
        return new double[]{x, y};
    }
}
