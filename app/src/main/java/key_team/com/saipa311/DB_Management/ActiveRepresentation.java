package key_team.com.saipa311.DB_Management;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by ammorteza on 12/22/17.
 */
@Table(name = "tbl_active_representation")
public class ActiveRepresentation extends Model {
    @Column(name = "repId")
    public Integer repId;

    @Column(name = "name")
    public String name;

    @Column(name = "code")
    public String code;

    public ActiveRepresentation(){
        super();
    }

/*    public UserInfo(String name , String mobile , String access_token , String refresh_token)
    {
        super();
        this.name = name;
        this.mobile = mobile;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }*/

    public static ActiveRepresentation getActiveRepresentationInfo()
    {
        return new Select()
                .from(ActiveRepresentation.class)
                .executeSingle();
    }

    public static boolean activeRepresentationIsSet()
    {
        int activeRepCount = new Select().from(ActiveRepresentation.class).execute().size();
        if (activeRepCount == 0)
            return false;
        else
            return true;
    }

    public static int getActiveRepresentationId()
    {
        ActiveRepresentation activeRepresentation = ActiveRepresentation.getActiveRepresentationInfo();
        return activeRepresentation.repId;

    }
}
