#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/stat.h>
#include <signal.h>
#include <errno.h>
#include <sys/select.h>
#include <pthread.h>

#define SERVER_FIFO "MANAGER"

#define CLIENT_FIFO "FEED%d"
char CLIENT_FIFO_FINAL[100];

#define MAX_USERS 10
#define MAX_TOPICS 20
#define MAX_MSG_P 5

//----------------------------- Estruturas -------------------------
typedef struct
{
    int id;
    pid_t pid;
    char name[100];
    int flag;
} User;

typedef struct
{
    pid_t pid;
    char name[100];
} DataUser;

typedef struct
{
    int id;
    char command[100];
    char topic[20];
    int duration;
    char message[300];
    DataUser user;
} FeedCommands;

typedef struct
{
    // Users inscritos neste topico
    int numUsers;
    DataUser users[MAX_USERS];
} TopicUsers;

typedef struct
{
    // User que escreveu o topico
    char name[100];
    int duration;
    char msg[300];
} DataMessageP;

typedef struct
{
    int id;
    // Mensagens permanentes neste topico
    int num_msgP;
    DataMessageP messagesP[MAX_MSG_P];
} TopicMessagesP;

// Topic Geral
typedef struct
{
    char name[20];
    char status[60];
    int num_msgP;
} DataTopic;

typedef struct
{
    int id;
    int numTopics;
    DataTopic topics[MAX_TOPICS];
} TopicInfos;
//-------------------------------------------------------------------
// Estrutura creada para enviar dados
typedef struct
{
    User user;
    FeedCommands feedCommands;
    TopicUsers topicUsers;
    TopicMessagesP topicMessagesP;
    TopicInfos topicInfos;
} DataDados;

// Estrutura creada para armazenar dados
typedef struct
{
    DataTopic dataTopic;
    TopicUsers topicUsers;
    TopicMessagesP topicMessagesP;
    pthread_mutex_t *m;
} Topic;