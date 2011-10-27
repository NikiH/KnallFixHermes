package ch.hszt.kfh.ubungen;

public interface IMdpJunitDemo {

    /**
     * @param s
     *            string to inspect
     * @return true if string is <code>null</code> or has 0 length
     */
    public boolean isEmpty(String s);

    /**
     * Returns a copy of str with the first character converted to uppercase and
     * the remainder to lowercase.
     * 
     * <pre>
     * "hello".capitalize    #=> "Hello"
     * "HELLO".capitalize    #=> "Hello"
     * "123ABC".capitalize   #=> "123abc"
     * </pre>
     * 
     * @param s
     *            string to capitalize
     * @return capitalized string
     */
    public String capitalize(String s);

    /**
     * Reverses the given String character by character. E.g.
     * <code>"Test Hsz"</code> becomes <code>"zsH tesT"</code>
     * 
     * @param s
     *            string to reverse
     * @return reversed string
     * @throws NullPointerException
     *             when given argument is null
     */
    public String reverse(String s) throws NullPointerException;

    /**
     * Joins the given strings to one string. Each given string is separated by
     * a whitespace, but there will be no trailing whitespaces.
     * 
     * E.g. <code>join("a","b","c")<code> will result in 
     * <code>"a b c"<code>.
     * 
     * @param strings
     *            strings to join
     * @return the concatenated string
     */
    public String join(String... strings);

}
