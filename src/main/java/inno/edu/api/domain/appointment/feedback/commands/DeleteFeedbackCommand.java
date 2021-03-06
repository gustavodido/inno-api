package inno.edu.api.domain.appointment.feedback.commands;

import inno.edu.api.domain.appointment.feedback.assertions.FeedbackExistsAssertion;
import inno.edu.api.domain.appointment.feedback.respositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteFeedbackCommand {
    private final FeedbackExistsAssertion feedbackExistsAssertion;
    private final FeedbackRepository feedbackRepository;

    public DeleteFeedbackCommand(FeedbackExistsAssertion feedbackExistsAssertion, FeedbackRepository feedbackRepository) {
        this.feedbackExistsAssertion = feedbackExistsAssertion;
        this.feedbackRepository = feedbackRepository;
    }

    public void run(UUID id) {
        feedbackExistsAssertion.run(id);
        feedbackRepository.delete(id);
    }
}
