package edu.tcu.cs.backend.crewmember;


import org.springframework.stereotype.Service;

@Service
public class CrewMemberService {

    private final CrewMemberRespository Repo;

    public CrewMemberService(CrewMemberRespository Repo, CrewMemberRespository repo) {
        this.Repo = repo;
    }

    public CrewMember getCrewMemberById(Long id) {
        return Repo.findById(id)
                .orElse(null);
    }
}
