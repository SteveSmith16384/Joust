package com.scs.awt;


// Origin is top-left!

public class RectF {

	public float left, top, right, bottom;

	public RectF() {
	}


	public RectF(float l, float t, float r, float b) {
		left = l;
		top = t;
		right = r;
		bottom = b;
		
		if (t > b) {
			throw new RuntimeException("Invert rectangle!");
		}
	}


	public void set(float l, float t, float r, float b) {
		left = l;
		top = t;
		right = r;
		bottom = b;

		if (t > b) {
			throw new RuntimeException("Invert rectangle!");
		}
}


	public void set(RectF r) {
		left = r.left;
		top = r.top;
		right = r.right;
		bottom = r.bottom;

		if (top > bottom) {
			throw new RuntimeException("Invert rectangle!");
		}
}


	public boolean intersect(float left, float top, float right, float bottom) {
		if (this.left < right && left < this.right
				&& this.top < bottom && top < this.bottom) {
			if (this.left < left) {
				this.left = left;
			}
			if (this.top < top) {
				this.top = top;
			}
			if (this.right > right) {
				this.right = right;
			}
			if (this.bottom > bottom) {
				this.bottom = bottom;
			}
			return true;
		}
		return false;
	}


	public static boolean intersects(RectF a, RectF b) {
		return a.left < b.right && b.left < a.right
				&& a.top < b.bottom && b.top < a.bottom;
	}


	public boolean intersects(RectF a) {
		return a.left < this.right && this.left < a.right
				&& a.top < this.bottom && this.top < a.bottom;
	}


	public boolean contains(float x, float y) {
		return left < right && top < bottom  // check for empty first
				&& x >= left && x < right && y >= top && y < bottom;
	}



	public final float centerX() {
		return (left + right) * 0.5f;
	}


	public final float centerY() {
		return (top + bottom) * 0.5f;
	}


	public final boolean isEmpty() {
        return left >= right || top >= bottom;
    }

    /**
     * @return the rectangle's width. This does not check for a valid rectangle
     * (i.e. left <= right) so the result may be negative.
     */
    public final float width() {
        return right - left;
    }

    /**
     * @return the rectangle's height. This does not check for a valid rectangle
     * (i.e. top <= bottom) so the result may be negative.
     */
    public final float height() {
        return bottom - top;
    }
    
    
    public float getX() {
    	return this.left;
    }
    
    
    public float getY() {
    	return this.top;
    }
    
    
    public void move(float offx, float offy) {
    	this.left += offx;
    	this.right += offx;
    	this.top += offy;
    	this.bottom += offy;
    }

}
