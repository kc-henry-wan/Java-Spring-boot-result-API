{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww21160\viewh13800\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.hope.apiapp.service;\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 \
@RunWith(SpringRunner.class)\
@SpringBootTest\
\
public class NegotiationServiceTest \{\
\
    @Autowired\
    private NegotiationService negotiationService;\
\
    @MockBean\
    private NegotiationRepository negotiationRepository;\
\
    @Test\
    public void testUpdateNegotiation_SuccessfulUpdate() \{\
        // Arrange\
        Long id = 1L;\
        Double newHourlyRate = 60.0;\
        LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);\
\
        Negotiation existingNegotiation = new Negotiation(id, 50.0, originalLastModifiedDate);\
\
        Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));\
\
        // Act\
        boolean result = negotiationService.updateNegotiation(id, newHourlyRate, originalLastModifiedDate);\
\
        // Assert\
        assertTrue(result);\
        assertEquals(newHourlyRate, existingNegotiation.getHourlyRate());\
        Mockito.verify(negotiationRepository).save(existingNegotiation);\
    \}\
\
    @Test\
    public void testUpdateNegotiation_RecordModified() \{\
        // Arrange\
        Long id = 1L;\
        Double newHourlyRate = 60.0;\
        LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);\
        LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);\
\
        Negotiation existingNegotiation = new Negotiation(id, 50.0, updatedLastModifiedDate);\
\
        Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));\
\
        // Act\
        boolean result = negotiationService.updateNegotiation(id, newHourlyRate, originalLastModifiedDate);\
\
        // Assert\
        assertFalse(result);\
        Mockito.verify(negotiationRepository, Mockito.never()).save(existingNegotiation);\
    \}\
\
    @Test\
    public void testUpdateNegotiation_RecordNotFound() \{\
        // Arrange\
        Long id = 1L;\
        Double newHourlyRate = 60.0;\
        LocalDateTime lastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);\
\
        Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.empty());\
\
        // Act\
        boolean result = negotiationService.updateNegotiation(id, newHourlyRate, lastModifiedDate);\
\
        // Assert\
        assertFalse(result);\
        Mockito.verify(negotiationRepository, Mockito.never()).save(Mockito.any(Negotiation.class));\
    \}\
\}\
}