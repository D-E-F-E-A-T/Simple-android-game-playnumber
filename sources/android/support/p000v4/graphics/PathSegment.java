package android.support.p000v4.graphics;

import android.graphics.PointF;
import android.support.p000v4.util.Preconditions;

/* renamed from: android.support.v4.graphics.PathSegment */
public final class PathSegment {
    private final PointF mEnd;
    private final float mEndFraction;
    private final PointF mStart;
    private final float mStartFraction;

    public PathSegment(PointF start, float startFraction, PointF end, float endFraction) {
        this.mStart = (PointF) Preconditions.checkNotNull(start, "start == null");
        this.mStartFraction = startFraction;
        this.mEnd = (PointF) Preconditions.checkNotNull(end, "end == null");
        this.mEndFraction = endFraction;
    }

    public PointF getStart() {
        return this.mStart;
    }

    public float getStartFraction() {
        return this.mStartFraction;
    }

    public PointF getEnd() {
        return this.mEnd;
    }

    public float getEndFraction() {
        return this.mEndFraction;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof PathSegment)) {
            return false;
        }
        PathSegment that = (PathSegment) o;
        if (Float.compare(this.mStartFraction, that.mStartFraction) != 0 || Float.compare(this.mEndFraction, that.mEndFraction) != 0 || !this.mStart.equals(that.mStart) || !this.mEnd.equals(that.mEnd)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int hashCode = this.mStart.hashCode() * 31;
        float f = this.mStartFraction;
        int i = 0;
        int result = (((hashCode + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31) + this.mEnd.hashCode()) * 31;
        float f2 = this.mEndFraction;
        if (f2 != 0.0f) {
            i = Float.floatToIntBits(f2);
        }
        return result + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PathSegment{start=");
        sb.append(this.mStart);
        sb.append(", startFraction=");
        sb.append(this.mStartFraction);
        sb.append(", end=");
        sb.append(this.mEnd);
        sb.append(", endFraction=");
        sb.append(this.mEndFraction);
        sb.append('}');
        return sb.toString();
    }
}
