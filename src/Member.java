public class Member {
    private int memberId;
    private String name;
    private String address;
    private String phoneNumber;

    public Member(int memberId, String name, String address, String phoneNumber) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
