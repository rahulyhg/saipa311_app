package key_team.com.saipa311.DB_Management;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by ammorteza on 12/22/17.
 */
@Table(name = "tbl_user_info")
public class UserInfo extends Model {
    @Column(name = "name")
    public String name;

    @Column(name = "mobile")
    public String mobile;

    @Column(name = "access_token")
    public String access_token;

    @Column(name = "refresh_token")
    public String refresh_token;

    public UserInfo(){
        super();
    }

    public UserInfo(String name , String mobile , String access_token , String refresh_token)
    {
        super();
        this.name = name;
        this.mobile = mobile;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public static UserInfo getUserInfo()
    {
        return new Select()
                .from(UserInfo.class)
                .executeSingle();
    }
}
