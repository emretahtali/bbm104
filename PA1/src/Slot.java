public class Slot
{
    private String _name = "___";
    private int _price = 0;
    private float _calories = 0f;
    private float _prot = 0f;
    private float _carb = 0f;
    private float _fat = 0f;
    private int _amount = 0;

    /**
     * Sets the properties of the slot.
     *
     * @param name     The name of the item.
     * @param price    The price of the item.
     * @param calories The calories of the item.
     * @param prot     The protein content of the item.
     * @param carb     The carbohydrate content of the item.
     * @param fat      The fat content of the item.
     * @param amount   The amount of the item in the slot.
     */
    void set(String name, int price, float calories, float prot, float carb, float fat, int amount)
    {
        _name = name;
        _price = price;
        _calories = calories;
        _prot = prot;
        _carb = carb;
        _fat = fat;
        _amount = amount;
    }

    /**
     * Gets the name of the item in the slot.
     *
     * @return The name of the item.
     */
    String getName() { return _name; }

    /**
     * Gets the price of the item in the slot.
     *
     * @return The price of the item.
     */
    int getPrice() { return _price; }

    /**
     * Gets the calorie content of the item in the slot.
     *
     * @return The calorie content of the item.
     */
    float getCals() { return _calories; }

    /**
     * Gets the protein content of the item in the slot.
     *
     * @return The protein content of the item.
     */
    float getProt() { return _prot; }

    /**
     * Gets the carbohydrate content of the item in the slot.
     *
     * @return The carbohydrate content of the item.
     */
    float getCarb() { return _carb; }

    /**
     * Gets the fat content of the item in the slot.
     *
     * @return The fat content of the item.
     */
    float getFat() { return _fat; }

    /**
     * Gets the amount of the item in the slot.
     *
     * @return The amount of the item.
     */
    int getAmount() { return _amount; }

    /**
     * Adds one item to the slot.
     */
    void addItem() { _amount ++; }

    /**
     * Takes one item from the slot.
     * If the slot becomes empty, sets it to a default empty state.
     */
    void takeItem()
    {
        _amount --;

        if (_amount == 0) this.set("___", 0, 0f, 0f, 0f, 0f, 0);
    }

}