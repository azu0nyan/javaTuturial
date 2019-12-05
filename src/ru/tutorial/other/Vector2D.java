package ru.tutorial.other;

public class Vector2D {

    static final Vector2D zeroVector = new Vector2D(0, 0);
    static final Vector2D xVector = new Vector2D(1, 0);
    static final Vector2D yVector = new Vector2D(0, 1);
    public static Vector2D infVector = new Vector2D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);


    private static double rotationDirection = 1;
    public double x, y = 0;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Суммирует вектор с аргументом покоординатно
     *
     * @param v слагаемое
     * @return сумма вектора и аргумента
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    /**
     * @param v1 слагаемое 1
     * @param v2 слагаемое 2
     * @return покоординатная сумма v1  + v2
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return v1.add(v2);
    }

    /**
     * Умножает вектора покоординатно
     *
     * @param v множитель
     * @return произведение вектора и аргумента
     */
    public Vector2D mull(Vector2D v) {
        return new Vector2D(x * v.x, y * v.y);
    }

    /**
     * @param v1 множитель 1
     * @param v2 множитель 2
     * @return покоординатное произведение v1 * v2
     */
    public static Vector2D mull(Vector2D v1, Vector2D v2) {
        return v1.mull(v2);
    }

    /**
     * Вычитает аргумент из вектора покоординатно
     *
     * @param v вычитаемое
     * @return разность вектора и аргумента
     */
    public Vector2D sub(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    /**
     * @param v1 уменьшаемое
     * @param v2 ввычитаемое
     * @return покоординатная разность v1 - v2
     */
    public static Vector2D sub(Vector2D v1, Vector2D v2) {
        return v1.sub(v2);
    }

    /**
     * Делит вектор на аргумент покоординатно
     *
     * @param v делитель
     * @return частное вектора и аргумента
     */
    public Vector2D div(Vector2D v) {
        return new Vector2D(v.x == 0 ? 0 : x / v.x, v.y == 0 ? 0 : y / v.y);
    }

    /**
     * @param v1 делимое
     * @param v2 делитель
     * @return покоординатное частное v1 / v2
     */
    public static Vector2D div(Vector2D v1, Vector2D v2) {
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
    public Vector2D scale(double d) {
        return new Vector2D(x * d, y * d);
    }

    /**
     * Масштабирует вектор
     *
     * @param v вектор
     * @param d масштаб
     * @return масштабированный вектор
     */
    public static Vector2D scale(Vector2D v, double d) {
        return v.scale(d);
    }

    /**
     * @return вектор направленный в противоположную сторону(развернутый на 180 градусов), такой же длинны
     */
    public Vector2D opposite() {
        return scale(-1);
    }

    /**
     * @param v ветор
     * @return вектор направленный в противоположную сторону(развернутый на 180 градусов), такой же длинны
     */
    public static Vector2D opposite(Vector2D v) {
        return v.scale(-1);
    }

    /**
     * поворачивает вектор на угол, против часовой стрелки(в обычной системе координат)
     *
     * @param a угол поворота
     * @return вектор повернутый на угол а
     */
    public Vector2D rotate(double a) {
        a *= rotationDirection;
        return new Vector2D((double) (x * Math.cos(a) + y * Math.sin(a)), (double) (x * -Math.sin(a) + y * Math.cos(a)));
    }

    /**
     * поворачивает вектор на угол, против часовой стрелки(в обычной системе координат)
     *
     * @param a угол поворота
     * @param v вектор
     * @return вектор повернутый на угол а
     */
    public static Vector2D rotate(Vector2D v, double a) {
        return v.rotate(a);
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
    public static double length(Vector2D v) {
        return v.length();
    }

    /**
     * Нормализует вектор
     *
     * @return вектор длинны 1 смотрящий в том же направлении что и данный
     */
    public Vector2D normalize() {
        if (length() == 0) {
            return new Vector2D(0, 0);
        }
        return scale(1 / length());
    }

    /**
     * Нормализует вектор
     *
     * @param v вектор
     * @return вектор длинны 1 смотрящий в том же направлении что и данный
     */
    public static Vector2D normalize(Vector2D v) {
        return v.normalize();
    }

    /**
     * скалярное произведение
     *
     * @param v множитель
     * @return скалярное произведенние вектора с аргументом функции
     */
    public double scalarProduct(Vector2D v) {
        return x * v.x + y * v.y;
    }

    /**
     * Скалярное произведение
     *
     * @param v1 множитель 1
     * @param v2 множитель 2
     * @return скалярное произведение v1 и v2
     */
    public static double scalarProduct(Vector2D v1, Vector2D v2) {
        return v1.scalarProduct(v2);
    }

    /**
     * @param v
     * @return ориентированный угол между вектором и аргументом функции от -Пи до Пи
     */
    public double angle(Vector2D v) {
        return (double) (Math.atan2(v.y, v.x) - Math.atan2(y, x));
    }

    /**
     * @param v1 вектор 1
     * @param v2 вектор 2
     * @return ориентированный угол между v1 и v2
     */
    public static double angle(Vector2D v1, Vector2D v2) {
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

    protected static double getRotationDirection() {
        return rotationDirection;
    }

    protected static void setRotationDirection(double rotationDirection) {
        Vector2D.rotationDirection = rotationDirection;
    }

    public double distance(Vector2D v) {
        return distance(this, v);
    }

    public static double distance(Vector2D v1, Vector2D v2) {
        return v1.sub(v2).length();
    }



    public double[] toArray() {
        return new double[]{x, y};
    }
}
