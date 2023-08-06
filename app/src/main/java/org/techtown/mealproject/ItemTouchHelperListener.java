package org.techtown.mealproject;

public interface ItemTouchHelperListener {
    void onItemClick(int position);
    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
}
