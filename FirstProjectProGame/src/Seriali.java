import java.io.Serializable;

public class Seriali implements Serializable {
    boolean isWeaponAdded1;
    boolean isAlwaysChange1;
    boolean isTrue1;
    int s1;
    int cost1;
    int speedBullet1;

    public Seriali(boolean wea, boolean alw, boolean ist, int s, int cos, int spe)
    {
        isWeaponAdded1 = wea;
        isAlwaysChange1 = alw;
        isTrue1 = ist;
        s1 = s;
        cost1 = cos;
        speedBullet1 = spe;
    }
    public boolean getWeap()
    {
        return isWeaponAdded1;
    }
    public boolean getAlwa()
    {
        return isAlwaysChange1;
    }
    public boolean getIsTr()
    {
        return isTrue1;
    }
    public int getS()
    {
        return s1;
    }
    public int getCos()
    {
        return cost1;
    }
    public int getSpeed()
    {
        return speedBullet1;
    }
}
