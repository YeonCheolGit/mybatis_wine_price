package main.DAO.member;

import main.DTO.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private final SqlSession sqlSession;

    public MemberDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private static final String nameSpace = "mapper.member";

    @Override
    public void registerMember(MemberDTO memberDTO) {
        sqlSession.insert(nameSpace + ".registerMember", memberDTO);
    }

//    @Override
//    public void registerMember(String id, String pwd, String name) {
//        sqlSession.insert(nameSpace + ".registerMember", id +  pwd + name);
//    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        return sqlSession.selectOne(nameSpace + ".login", memberDTO);
    }

    @Override
    public int duplicatedIdChk(MemberDTO memberDTO) {
        return sqlSession.selectOne(nameSpace + ".duplicatedIdChk", memberDTO);
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {
        sqlSession.update(nameSpace + ".memberUpdate", memberDTO);
    }

}
