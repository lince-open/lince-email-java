package work.lince.project.service

import spock.lang.Specification
import spock.lang.Unroll
import work.lince.commons.authentication.AuthenticationService
import work.lince.project.model.Project
import work.lince.project.model.EmailStatus
import work.lince.project.repository.ProjectRepository
/**
 * @author pzatta
 */

class ProjectServiceSpec extends Specification {

    EmailService service;

    def setup() {
        service = Spy(EmailService)
        service.repository = Mock(ProjectRepository)
        service.authenticationService = Mock(AuthenticationService)

    }

    @Unroll
    def "verify with #title"() {
        given:
            1 * service.repository.save(_) >> { Project value ->
                value.id = id
                return value
            }
            1 * service.authenticationService.getAuthenticatedUser() >> { user }
            def project = new Project(
                title: title,
                status: status,
                owner: owner
            )
        when:
            def result = service.create(project)

        then:
            result != null
            result.id == id
            result.title == title
            result.owner == user
            result.status == EmailStatus.CREATED

        where:
            title             | status             | owner      | user   | id
            "Project Title 1" | null               | "asdfasdf" | "asdf" | 1L
            "Project Title 2" | EmailStatus.CLOSED | null       | "qwer" | 2L
            "Project Title 3" | null               | null       | "asdf" | 3L
            "Project Title 4" | EmailStatus.CLOSED | "asdfasdf" | "qwer" | 4L


    }

}