package cn.teampancake.theaurorian.common.level.data.world_event;

public class BloodMoonPlayerData {

    public int kills;
    public boolean rewardActive;
    public boolean penaltyActive;
    public long rewardUntil;

    public BloodMoonPlayerData() {
        this.kills = 0;
        this.rewardActive = false;
        this.penaltyActive = false;
        this.rewardUntil = 0;
    }

}
