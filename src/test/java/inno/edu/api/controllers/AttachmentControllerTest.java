package inno.edu.api.controllers;

import inno.edu.api.domain.attachment.models.resources.AttachmentResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.attachment.commands.CreateAttachmentCommand;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.attachment.commands.UpdateAttachmentCommand;
import inno.edu.api.domain.attachment.models.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.attachment.queries.GetAttachmentByIdQuery;
import inno.edu.api.domain.attachment.queries.GetAttachmentsQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static inno.edu.api.support.AttachmentFactory.feiProfilePhoto;
import static inno.edu.api.support.AttachmentFactory.attachments;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.updateAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.updatedAttachment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetAttachmentsQuery getAttachmentsQuery;

    @Mock
    private GetAttachmentByIdQuery getAttachmentByIdQuery;

    @Mock
    private CreateAttachmentCommand createAttachmentCommand;

    @Mock
    private DeleteAttachmentCommand deleteAttachmentCommand;

    @Mock
    private UpdateAttachmentCommand updateAttachmentCommand;

    @InjectMocks
    private AttachmentController attachmentController;

    @Test
    public void shouldGetAttachmentById() {
        when(getAttachmentByIdQuery.run(eq(feiProfilePhoto().getId()))).thenReturn(feiProfilePhoto());

        AttachmentResource attachmentResource = attachmentController.get(feiProfilePhoto().getId());

        assertThat(attachmentResource.getAttachment(), is(feiProfilePhoto()));
    }

    @Test
    public void shouldListAttachments() {
        when(getAttachmentsQuery.run()).thenReturn(attachments());

        attachmentController.all();

        verify(resourceBuilder).wrappedFrom(eq(attachments()), any(), eq(AttachmentResource.class));
    }

    @Test
    public void shouldCreateAttachment() {
        CreateAttachmentRequest request = createAttachmentRequest();

        when(createAttachmentCommand.run(request)).thenReturn(feiProfilePhoto());

        AttachmentResource attachmentResource = attachmentController.post(
                request.getDescription(),
                request.getFile()
        );

        assertThat(attachmentResource.getAttachment(), is(feiProfilePhoto()));
    }

    @Test
    public void shouldUpdateAttachment() {
        when(updateAttachmentCommand.run(feiProfilePhoto().getId(), updateAttachmentRequest())).thenReturn(updatedAttachment());

        ResponseEntity<Attachment> entity = attachmentController.put(feiProfilePhoto().getId(), updateAttachmentRequest());

        assertThat(entity.getBody(), is(updatedAttachment()));
    }

    @Test
    public void shouldDeleteAttachment() {
        attachmentController.delete(feiProfilePhoto().getId());

        verify(deleteAttachmentCommand).run(feiProfilePhoto().getId());
    }
}