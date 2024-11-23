package com.example.eateasy.Adapter.User;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class ScrollLineDecoration extends RecyclerView.ItemDecoration {

    private Paint paint;
    private boolean isScrolled = false;  // Kiểm tra khi cuộn

    public ScrollLineDecoration() {
        paint = new Paint();
        paint.setColor(Color.BLACK); // Màu đường line
        paint.setStrokeWidth(1);    // Độ dày của đường line
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        // Vẽ đường kẻ chỉ khi cuộn
        if (isScrolled) {
            // Vẽ đường kẻ ở vị trí mong muốn (trên cùng của RecyclerView)
            c.drawLine(0, 0, parent.getWidth(), 0, paint);  // Vẽ từ bên trái tới bên phải
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // Không thay đổi khoảng cách cho item
    }

    // Phương thức để cập nhật trạng thái cuộn
    public void setScrolled(boolean isScrolled) {
        this.isScrolled = isScrolled;
    }
}
