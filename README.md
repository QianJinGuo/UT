# SUMMARY
## Mockito
1. Understand mocking theory
2. create mocks and spies
3. mock typical methods
4. apply matches and argument captors
5. use the verify() method
6. work with annotations, BDD style methods and strict stubbing
7. mock static and final methods (don't try to mock private methods)
8. Focus on Mockito only.It already has the features you need! (It would be best not to depend on additional libraries such as PowerMock too much)


My personal opinion as a software developer is that you no longer need to use PowerMock for your mocks.
Mockito has introduced enough new features,and you can use it without PowerMock now.
The Mockito team also plans to add even more functionality.I am sure that there will come a time when
Mockito has all the features of PowerMock in place,and there will simply be no need to use it at all.

## Spring Boot Tests
### Unit Tests
- You can use @ExtendWith(MockitoExtension.class)
- Spring Context not initialized
- Shorter test execution time
- You can use standard Mockito annotations： @Mock,@InjectMocks etc.

### Integration Tests
- Use @SpringBootTest
- Spring Context initialized
- Longer test execution time
- Use @MockBean to replace beans with mocked beans