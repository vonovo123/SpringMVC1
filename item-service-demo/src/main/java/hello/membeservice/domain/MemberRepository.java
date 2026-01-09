package hello.membeservice.domain;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    private static Long sequence = 0L;
    private DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

    public Member insertMember(Member member) throws SQLException {
        member.setId(UUID.randomUUID().toString());
        String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,member.getId());
            pstmt.setString(2, member.getLoginId());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getPassword());
            pstmt.setInt(5, member.getPoint());
            pstmt.setString(6, member.getAcctNo());
            pstmt.setString(7, member.getBankCd());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,null);
        }
    }

    public Member selectById(String id) throws SQLException {
        String sql = "select * from member where id = ?";
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                Member newMem = new Member();
                newMem.setId(rs.getString("id"));
                newMem.setLoginId(rs.getString("login_id"));
                newMem.setName(rs.getString("name"));
                newMem.setPassword(rs.getString("password"));
                newMem.setPoint(rs.getInt("point"));
                newMem.setAcctNo(rs.getString("acct_no"));
                newMem.setBankCd(rs.getString("bank_cd"));
                return newMem;
            } else {
                throw new NoSuchElementException("member not found memberId = " + id);
            }
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,rs);
        }
    }
    public Member selectByAcctNo(String acctNo) throws SQLException {
        String sql = "select * from member where acct_no = ?";
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, acctNo);
            rs = pstmt.executeQuery();
            if(rs.next()){
                Member newMem = new Member();
                newMem.setId(rs.getString("id"));
                newMem.setLoginId(rs.getString("login_id"));
                newMem.setName(rs.getString("name"));
                newMem.setPassword(rs.getString("password"));
                newMem.setPoint(rs.getInt("point"));
                newMem.setAcctNo(rs.getString("acct_no"));
                newMem.setBankCd(rs.getString("bank_cd"));
                return newMem;
            } else {
                throw new NoSuchElementException("member not found memberAcctNO = " + acctNo);
            }
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,rs);
        }
    }
    public Member selectByLoginId(String loginId) throws SQLException {
        String sql = "select * from member where login_id = ?";
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                Member newMem = new Member();
                newMem.setId(rs.getString("id"));
                newMem.setLoginId(rs.getString("login_id"));
                newMem.setName(rs.getString("name"));
                newMem.setPassword(rs.getString("password"));
                newMem.setPoint(rs.getInt("point"));
                newMem.setAcctNo(rs.getString("acct_no"));
                newMem.setBankCd(rs.getString("bank_cd"));
                return newMem;
            } else {
                throw new NoSuchElementException("member not found memberLoginId = " + loginId);
            }
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,rs);
        }
    }

    public int updatePoint( Member member) throws SQLException {
        Connection con = null;
        String sql = "update member set point= ? where id = ?";
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,member.getPoint());
            pstmt.setString(2, member.getId());
            int updateSize = pstmt.executeUpdate();

            if(updateSize > 0){
                return updateSize;
            } else {
                throw new NoSuchElementException("update fail id = " + member.getId());
            }
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,null);

        }
    }

    public int updatePassword(Member member) throws SQLException {
        String sql = "update member set password = ? where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,member.getPassword());
            pstmt.setString(2, member.getId());
            int updateSize = pstmt.executeUpdate();

            if(updateSize > 0){
                return updateSize;
            } else {
                throw new NoSuchElementException("update fail id = " + member.getId());
            }
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,null);
        }
    }

    public void deleteMember(Long id) throws SQLException {
        String sql = "delete member where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,null);
        }
    }

    public void removeAll() throws SQLException {
        String sql = "delete member";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,null);
        }
    }
    public List<Member> selectAll() throws SQLException {
        String sql = "select * from member";

        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Member> members = new ArrayList<>();
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Member newMem = new Member();
                newMem.setId(rs.getString("id"));
                newMem.setLoginId(rs.getString("login_id"));
                newMem.setName(rs.getString("name"));
                newMem.setPassword(rs.getString("password"));
                newMem.setPoint(rs.getInt("point"));
                newMem.setAcctNo(rs.getString("acct_no"));
                newMem.setBankCd(rs.getString("bank_cd"));
                members.add(newMem);
            }
            return members;
        } catch (SQLException e) {
            log.error("db error ={}", e);
            throw e;
        } finally {
            close(con,pstmt,null);
        }
    }

    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);
        DataSourceUtils.releaseConnection(con,dataSource);
    }
}
