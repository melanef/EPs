int mdc(int x, int y) {
    if (y == 0) {
        return x;
    }

    if (x == y) {
        return x;
    }

    if (x < y) {
        return mdc(y,x);
    }

    int r = x % y;
    if (r == 0) {
        return y;
    }

    return mdc(y, r);
}

