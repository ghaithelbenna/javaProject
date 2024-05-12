package tn.esprit.models;

public class packPersonnaliser {
    private int id;
    private int packId;
    private int programmeId;

    public packPersonnaliser(int id, int packId, int programmeId) {
        this.id = id;
        this.packId = packId;
        this.programmeId = programmeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackId() {
        return packId;
    }

    public void setPackId(int packId) {
        this.packId = packId;
    }

    public int getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(int programmeId) {
        this.programmeId = programmeId;
    }

    @Override
    public String toString() {
        return "PackPersonnaliser{" +
                "id=" + id +
                ", packId=" + packId +
                ", programmeId=" + programmeId +
                '}';
    }
}
