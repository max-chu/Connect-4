// Slot class for each of the connect 4 slots
 
public class Slot {

    private char SlotPosition;
    private int  rowPosition;
    private int  SlotState;

    /**
     * No parameter constructor
     */
    public Slot()
    {
        SlotPosition = ' ';
        rowPosition  =  0;
        SlotState = 0;
    }

    /**
     * Constructor that takes Slot position and row position
     * @param newSlotPosition character to Slot position
     * @param newRowPosition integer to row position
     */
    public Slot( char newSlotPosition,  int newRowPosition)
    {
        SlotPosition = newSlotPosition;
        rowPosition  =  newRowPosition;
    }

    /**
     * Set the Slot position
     * @param newSlotPosition character to Slot position
     */
    public void  setSlotPosition(char newSlotPosition)
    {
        SlotPosition = newSlotPosition;
    }

    /**
     * Set the row position
     * @param newRowPosition integer to row position
     */
    public void setRowPosition( int newRowPosition)
    {
        rowPosition = newRowPosition;
    }

    /**
     * Set Slot and row position
     * @param newSlotPosition character to Slot position
     * @param newRowPosition  integer to row position
     */
    public void setAllPosition( char newSlotPosition, int newRowPosition)
    {
        SlotPosition =  newSlotPosition;
        rowPosition  =  newRowPosition;
    }

    /**
     * Set the Slot state
     * @param newSlotState integer to Slot state
     */
    public void  setSlotState(int newSlotState)
    {
        SlotState = newSlotState;
    }

    /**
     * Getter to Slot state
     * @return Slot state
     */
    public int getSlotState()
    {
        return SlotState;
    }

    /**
     * Get Slot Position
     * @return Slot position
     */
    public char getSlotPosition()
    {
        return SlotPosition;
    }


    /**
     * Getter the row position
     * @return row position
     */
    public int getRowPosition()
    {
        return rowPosition;
    }


}
